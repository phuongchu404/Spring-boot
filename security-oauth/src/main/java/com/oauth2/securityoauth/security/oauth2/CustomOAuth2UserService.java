package com.oauth2.securityoauth.security.oauth2;

import com.oauth2.securityoauth.consts.ERole;
import com.oauth2.securityoauth.consts.Error;
import com.oauth2.securityoauth.consts.Provider;
import com.oauth2.securityoauth.dto.response.UserOAuth2Response;
import com.oauth2.securityoauth.entity.Role;
import com.oauth2.securityoauth.entity.User;
import com.oauth2.securityoauth.entity.UserRole;
import com.oauth2.securityoauth.exception.OAuth2AuthenticationProcessingException;
import com.oauth2.securityoauth.exception.ResponseException;
import com.oauth2.securityoauth.repo.RoleRepository;
import com.oauth2.securityoauth.repo.UserRepository;
import com.oauth2.securityoauth.repo.UserRoleRepository;
import com.oauth2.securityoauth.security.UserDetailsImpl;
import com.oauth2.securityoauth.security.oauth2.user.OAuth2UserInfo;
import com.oauth2.securityoauth.security.oauth2.user.OAuth2UserInfoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException{
        OAuth2User oAuth2User = super.loadUser(request);
        try {
            return processOAuth2User(request, oAuth2User);
        }catch (Exception exception){
            throw new InternalAuthenticationServiceException(exception.getMessage(), exception.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest request, OAuth2User oAuth2User){
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(request.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        if(StringUtils.isEmpty(oAuth2UserInfo.getEmail())){
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }
        Optional<User> userOptional = userRepository.findByEmail(oAuth2UserInfo.getEmail());
        UserOAuth2Response userOAuth2Response;
        if(userOptional.isPresent()){
            User user = userOptional.get();
            if(!user.getProvider().equals(Provider.valueOf(request.getClientRegistration().getRegistrationId()))) {
                throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
                        user.getProvider() + " account. Please use your " + user.getProvider() +
                        " account to login.");
            }
            userOAuth2Response = updateExistingUser(user, oAuth2UserInfo);
        }else{
            userOAuth2Response = registerNewUser(request, oAuth2UserInfo);
        }
        UserDetailsImpl userDetails = new UserDetailsImpl(userOAuth2Response.getId(), userOAuth2Response.getEmail(),userOAuth2Response.getUsername(), userOAuth2Response.getRoles(), oAuth2User.getAttributes());
        return userDetails;
    }

    private UserOAuth2Response registerNewUser(OAuth2UserRequest request, OAuth2UserInfo oAuth2UserInfo){
        User user = new User();
        user.setProvider(Provider.valueOf(request.getClientRegistration().getRegistrationId()));
        user.setProviderId(oAuth2UserInfo.getId());
        //user.setUsername(oAuth2UserInfo.getName());
        user.setUsername(oAuth2UserInfo.getEmail());
        user.setEmail(oAuth2UserInfo.getEmail());
        user.setImageUrl(oAuth2UserInfo.getImageUrl());
        user = userRepository.save(user);

        Role role = roleRepository.findByName(ERole.USER.getValue()).orElseThrow(() -> new ResponseException(Error.ROLE_NOT_FOUND));
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);
        userRole = userRoleRepository.save(userRole);
        Set<String> roles = Stream.of(userRole.getRole().getName()).collect(Collectors.toSet());

        UserOAuth2Response response = new UserOAuth2Response(user.getId(), user.getEmail(),user.getUsername(), roles);
        return response;
    }

    private UserOAuth2Response updateExistingUser(User existingUser, OAuth2UserInfo auth2UserInfo){
        existingUser.setName(auth2UserInfo.getName());
        existingUser.setImageUrl(auth2UserInfo.getImageUrl());

        Set<String> roles = userRoleRepository.findRolesByEmail(existingUser.getEmail()).stream().map(Role::getName).collect(Collectors.toSet());

        UserOAuth2Response response = new UserOAuth2Response(existingUser.getId(), existingUser.getEmail(),existingUser.getUsername(), roles);

        return response;
    }
}
