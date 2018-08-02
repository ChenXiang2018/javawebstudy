package manage.dao;

import manage.model.TMgtUser;

import java.util.Map;

public interface TMgtUserDAO {
    public TMgtUser login(Map<String, Object> map);
}
