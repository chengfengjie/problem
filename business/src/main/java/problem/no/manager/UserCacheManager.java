package problem.no.manager;

import problem.no.dto.user.CurrentUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;

public class UserCacheManager {

    public static final String KEY_USER_PREFIX = "USER_";

    @Autowired
    private CacheManager cacheManager;
    @Value("${spring.cache.cache-name-no-problem}")
    private String cacheName;

    private String buildCurrentUserKey(String authUserID) {
        return KEY_USER_PREFIX + authUserID;
    }

    public void setCurrentUserDto(CurrentUserDto currentUserDto) {
        cacheManager.getCache(cacheName).put(buildCurrentUserKey(currentUserDto.getAuthUserID()), currentUserDto);
    }

    public CurrentUserDto getCurrentUserDto(String authUserID) {
        return cacheManager.getCache(cacheName).get(buildCurrentUserKey(authUserID), CurrentUserDto.class);
    }

    public void removeCurrentUserDto(String authUserID) {
        if (authUserID != null) {
            cacheManager.getCache(cacheName).evict(buildCurrentUserKey(authUserID));
        }
    }

}
