package session;

import org.springframework.session.ExpiringSession;
import org.springframework.session.MapSession;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HashMapSessionRepository implements SessionRepository<ExpiringSession> {
    /**
     * If non-null, this value is used to override
     * {@link ExpiringSession#setMaxInactiveIntervalInSeconds(int)}.
     */
    private Integer defaultMaxInactiveInterval;

    private final Map<String, ExpiringSession> sessions;

    /**
     * Creates an instance backed by a {@link java.util.concurrent.ConcurrentHashMap}.
     */
    public HashMapSessionRepository() {
        this(new ConcurrentHashMap<String, ExpiringSession>());
    }

    /**
     * Creates a new instance backed by the provided {@link java.util.Map}. This allows
     * injecting a distributed {@link java.util.Map}.
     *
     * @param sessions the {@link java.util.Map} to use. Cannot be null.
     */
    public HashMapSessionRepository(Map<String, ExpiringSession> sessions) {
        if (sessions == null) {
            throw new IllegalArgumentException("sessions cannot be null");
        }
        this.sessions = sessions;
    }

    /**
     * If non-null, this value is used to override
     * {@link ExpiringSession#setMaxInactiveIntervalInSeconds(int)}.
     * @param defaultMaxInactiveInterval the number of seconds that the {@link Session}
     * should be kept alive between client requests.
     */
    public void setDefaultMaxInactiveInterval(int defaultMaxInactiveInterval) {
        this.defaultMaxInactiveInterval = Integer.valueOf(defaultMaxInactiveInterval);
    }

    public void save(ExpiringSession session) {
        System.out.println("save" + session.getId());
        this.sessions.put(session.getId(), new MapSession(session)

        );
    }

    public ExpiringSession getSession(String id) {
        ExpiringSession saved = this.sessions.get(id);
        if (saved == null) {
            return null;
        }
        if (saved.isExpired()) {
            delete(saved.getId());
            return null;
        }
        return new MapSession(saved);
    }

    public void delete(String id) {
        System.out.println("delete" + id);

        this.sessions.remove(id);
    }

    public ExpiringSession createSession() {
        ExpiringSession result = new MapSession();
        if (this.defaultMaxInactiveInterval != null) {
            result.setMaxInactiveIntervalInSeconds(this.defaultMaxInactiveInterval);
        }
        return result;
    }
}

