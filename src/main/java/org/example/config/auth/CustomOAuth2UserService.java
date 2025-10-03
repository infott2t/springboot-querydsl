package org.example.config.auth;

import lombok.RequiredArgsConstructor;
import org.example.config.auth.dto.OAuthAttributes;

import org.example.config.auth.dto.SessionUser;
import org.example.domain.user.User;
import org.example.domain.user.UserRepository;
import org.example.domain.userinfo.UserInfo;
import org.example.domain.userinfo.UserInfoRepository;
import org.example.domain.useruploadgradeadmin.UserUploadGradeAdmin;
import org.example.domain.useruploadgradeadmin.UserUploadGradeAdminRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;
    private final UserUploadGradeAdminRepository userUploadGradeAdminRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }


    private User saveOrUpdate(OAuthAttributes attributes) {
           User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        User make = userRepository.save(user);

        UserInfo userInfo = null;

        userInfo = userInfoRepository.findByEmail(user.getEmail()).orElse(attributes.toInfoEntity(user));
        if(userInfoRepository.findByEmail(user.getEmail()).isEmpty()){ userInfoRepository.save(userInfo);};

        UserUploadGradeAdmin userUploadGradeAdmin = null;
        userUploadGradeAdmin = userUploadGradeAdminRepository.findByEmail(user.getEmail()).orElse(attributes.toGradeEntity(user));
        if(userUploadGradeAdminRepository.findByEmail(user.getEmail()).isEmpty()){
            userUploadGradeAdminRepository.save(userUploadGradeAdmin);
        }


        return make;
    }
}
