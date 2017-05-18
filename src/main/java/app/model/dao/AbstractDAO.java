package app.model.dao;

import org.hibernate.Session;

public abstract class AbstractDAO {

    private Session mSession;

    Session getSession() {
        checkSession();
        return mSession;
    }

    public void setSession(Session session) {
        mSession = session;
    }

    private void checkSession(){
        if(mSession == null){
            throw new IllegalArgumentException("This method MUST be wrapped under DataEnvironment.class");
        }
    }
}
