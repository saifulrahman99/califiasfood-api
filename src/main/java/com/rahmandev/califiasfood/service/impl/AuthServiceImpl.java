package com.rahmandev.califiasfood.service.impl;

import com.rahmandev.califiasfood.constant.ResponseMessage;
import com.rahmandev.califiasfood.constant.UserRole;
import com.rahmandev.califiasfood.dto.request.RegisterRequest;
import com.rahmandev.califiasfood.dto.request.LoginRequest;
import com.rahmandev.califiasfood.dto.request.VerifyOtpRequest;
import com.rahmandev.califiasfood.dto.response.LoginResponse;
import com.rahmandev.califiasfood.dto.response.RegisterResponse;
import com.rahmandev.califiasfood.entity.Address;
import com.rahmandev.califiasfood.entity.Customer;
import com.rahmandev.califiasfood.entity.Role;
import com.rahmandev.califiasfood.entity.UserAccount;
import com.rahmandev.califiasfood.repository.UserAccountRepository;
import com.rahmandev.califiasfood.service.*;
import com.rahmandev.califiasfood.util.OTPUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserAccountRepository repository;
    private final RoleService roleService;
    private final AddressService addressService;
    private final PasswordEncoder passwordEncoder;
    private final EmailSenderService emailSenderService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Value("${califiasfood.email.superadmin}")
    private String superAdminEmail;
    @Value("${califiasfood.password.superadmin}")
    private String superAdminPassword;
    @Value("${califiasfood.name.superadmin}")
    private String nameSuperAdmin;
    @Value("${califiasfood.phoneNumber.superadmin}")
    private String phoneNumberSuperAdmin;
    @Value("${califiasfood.address.superadmin}")
    private String addressSuperAdmin;


    @Transactional(rollbackFor = Exception.class)
    @PostConstruct
    public void initSuperAdmin() {
        Optional<UserAccount> currentUser = repository.findByEmail(superAdminEmail);
        if (currentUser.isPresent()) return;

        Role superAdminRole = roleService.getOrSave(UserRole.ROLE_SUPER_ADMIN);
        Role adminRole = roleService.getOrSave(UserRole.ROLE_ADMIN);
        Role customerRole = roleService.getOrSave(UserRole.ROLE_CUSTOMER);
        Customer customer = Customer.builder()
                .name(nameSuperAdmin)
                .phoneNumber(phoneNumberSuperAdmin)
                .build();
        UserAccount account = UserAccount.builder()
                .email(superAdminEmail)
                .password(passwordEncoder.encode(superAdminPassword))
                .role(List.of(superAdminRole, adminRole, customerRole))
                .isEnabled(true)
                .createdAt(new Date())
                .customer(customer)
                .build();
        repository.saveAndFlush(account);
        Address address = Address.builder()
                .address(addressSuperAdmin)
                .customer(account.getCustomer())
                .build();
        addressService.create(address);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RegisterResponse register(RegisterRequest request) {
        Role role = roleService.getOrSave(UserRole.ROLE_CUSTOMER);
        String hashPassword = passwordEncoder.encode(request.getPassword());
        Customer customer = Customer.builder().name(request.getName()).phoneNumber(request.getPhoneNumber()).addresses(null).build();

        Integer otp = OTPUtil.generateOTP();

        UserAccount account = UserAccount.builder()
                .email(request.getEmail())
                .password(hashPassword)
                .role(List.of(role))
                .otp(otp)
                .isEnabled(false)
                .customer(customer)
                .createdAt(new Date())
                .build();
        UserAccount userAccount = repository.saveAndFlush(account);

        userAccount.getCustomer().setAddresses(List.of(
                Address.builder()
                        .address(request.getAddress())
                        .customer(account.getCustomer())
                        .build()
        ));
        // send otp verification
        emailSenderService.sendOtp(request.getEmail(), otp);

        List<String> roles = account.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        return RegisterResponse.builder()
                .userAccountId(account.getId())
                .idCustomer(customer.getId())
                .email(account.getEmail())
                .roles(roles)
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RegisterResponse registerAdmin(RegisterRequest request) {
        return null;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        );
        Authentication authenticate = authenticationManager.authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        UserAccount userAccount = (UserAccount) authenticate.getPrincipal();
        String token = jwtService.generateToken(userAccount);
        return LoginResponse.builder()
                .email(userAccount.getEmail())
                .roles(userAccount.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .userAccountId(userAccount.getId())
                .token(token)
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void verificationOtpToActiveAccount(VerifyOtpRequest request) {
        UserAccount userAccount = repository.findById(request.getUserAccountId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.USER_NOT_FOUND));
        if (userAccount.getOtp().equals(request.getOtpCode())) {
            userAccount.setOtp(null);
            userAccount.setIsEnabled(true);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ResponseMessage.INVALID_OTP);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void sendOtpAgain(String userAccountId) {
        UserAccount userAccount = repository.findById(userAccountId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.USER_NOT_FOUND));
        Integer otp = OTPUtil.generateOTP();
        userAccount.setOtp(otp);
        emailSenderService.sendOtp(userAccount.getEmail(), otp);
    }
}
