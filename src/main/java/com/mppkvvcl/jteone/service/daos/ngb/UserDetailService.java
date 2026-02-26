package com.mppkvvcl.jteone.service.daos.ngb;

import com.mppkvvcl.ngbdao.daos.UserDetailDAO;
import com.mppkvvcl.ngbdao.interfaces.UserDetailInterface;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailService {

    @Autowired
    private UserDetailDAO userDetailDAO;

    public UserDetailInterface getByUsername(String username) {
        if (StringUtils.isEmpty(username)) return null;

        return userDetailDAO.getByUsername(username);
    }

    public List<UserDetailInterface> getByLocationCodeAndRoleAndStatus(final String locationCode, final String role, String status) {
        if (StringUtils.isEmpty(locationCode) || StringUtils.isEmpty(role) || StringUtils.isEmpty(status)) return null;

        return userDetailDAO.getByLocationCodeAndRoleAndStatus(locationCode, role, status);
    }
}
