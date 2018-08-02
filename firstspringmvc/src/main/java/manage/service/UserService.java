package manage.service;

import manage.dao.TMgtUserDAO;
import manage.model.TMgtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService implements IUserService {

    @Autowired
    private TMgtUserDAO tMgtUserMapper;

    @Override
    public TMgtUser login(String userName, String pw) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put( "userName", userName);
        map.put( "pw", pw );
        return tMgtUserMapper.login( map );
    }
}
