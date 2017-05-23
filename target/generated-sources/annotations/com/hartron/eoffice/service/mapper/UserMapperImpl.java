package com.hartron.eoffice.service.mapper;

import com.hartron.eoffice.domain.User;

import com.hartron.eoffice.service.dto.UserDTO;

import java.util.ArrayList;

import java.util.HashSet;

import java.util.List;

import java.util.Set;

import javax.annotation.Generated;

import org.springframework.stereotype.Component;

@Generated(

    value = "org.mapstruct.ap.MappingProcessor",

    date = "2017-05-23T16:13:34+0530",

    comments = "version: 1.1.0.Final, compiler: javac, environment: Java 1.8.0_131 (Oracle Corporation)"

)

@Component

public class UserMapperImpl implements UserMapper {

    @Override

    public UserDTO userToUserDTO(User user) {

        if ( user == null ) {

            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( user.getId() );

        userDTO.setLogin( user.getLogin() );

        if ( userDTO.getAuthorities() != null ) {

            Set<String> set = user.getAuthorities();

            if ( set != null ) {

                userDTO.getAuthorities().addAll( set );
            }
        }

        return userDTO;
    }

    @Override

    public List<UserDTO> usersToUserDTOs(List<User> users) {

        if ( users == null ) {

            return null;
        }

        List<UserDTO> list = new ArrayList<UserDTO>();

        for ( User user : users ) {

            list.add( userToUserDTO( user ) );
        }

        return list;
    }

    @Override

    public User userDTOToUser(UserDTO userDTO) {

        if ( userDTO == null ) {

            return null;
        }

        User user = new User();

        user.setId( userDTO.getId() );

        user.setLogin( userDTO.getLogin() );

        user.setFirstName( userDTO.getFirstName() );

        user.setLastName( userDTO.getLastName() );

        user.setEmail( userDTO.getEmail() );

        user.setActivated( userDTO.isActivated() );

        user.setLangKey( userDTO.getLangKey() );

        Set<String> set = userDTO.getAuthorities();

        if ( set != null ) {

            user.setAuthorities(       new HashSet<String>( set )

            );
        }

        return user;
    }

    @Override

    public List<User> userDTOsToUsers(List<UserDTO> userDTOs) {

        if ( userDTOs == null ) {

            return null;
        }

        List<User> list = new ArrayList<User>();

        for ( UserDTO userDTO : userDTOs ) {

            list.add( userDTOToUser( userDTO ) );
        }

        return list;
    }
}

