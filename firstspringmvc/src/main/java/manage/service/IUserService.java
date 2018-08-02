package manage.service;

import manage.model.TMgtUser;

public interface IUserService {
    public TMgtUser login (String userName, String pw) throws Exception;
}
