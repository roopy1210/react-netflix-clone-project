package com.roopy.resolver;

import com.roopy.dto.UserDTO;
import com.roopy.entity.Member;
import com.roopy.netflix.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserDTO.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // 사용자 정보 조회
        Member member = userRepository.findOneWithAuthoritiesByUsername(user.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found with -> username or email: " + user.getUsername()));

        // 사용자 정보 설정
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(member.getUserId());
        userDTO.setUsername(member.getUsername());

        return userDTO;
    }
}
