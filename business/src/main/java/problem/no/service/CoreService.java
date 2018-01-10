package problem.no.service;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import problem.no.enums.user.UserRole;
import problem.no.model.user.UserModel;
import problem.no.repository.user.UserRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import problem.no.util.BusinessUtil;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;

@Service
public class CoreService {

    @Autowired
    private UserRepository userRepository;

    static private final String SUPER_ADMIN_PASSWORD = "superadmin";
    static private final String SUPER_ADMIN_EMAIL = "superadmin";
    static private final String SUPER_ADMIN_PHONE = "";

    private static final ThreadFactory NAMED_THREAD_FACTORY = new ThreadFactoryBuilder()
            .setNameFormat("Initlize-data-pool").build();

    private static final ExecutorService POOL = new ThreadPoolExecutor(1, 1,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(1024), NAMED_THREAD_FACTORY, new ThreadPoolExecutor.AbortPolicy());

    @PostConstruct
    public void initializeData() {
        POOL.execute(() -> {
            if (userRepository.queryUserByEmail(SUPER_ADMIN_EMAIL) == null) {
                Date currentDate = new Date();
                String salt = BusinessUtil.createPasswordSalt(currentDate);
                String encodePwd = BusinessUtil.encodePassword(SUPER_ADMIN_PASSWORD, salt);

                UserModel model = new UserModel(encodePwd, salt, currentDate);
                model.setEmail(SUPER_ADMIN_EMAIL);
                model.setCellphone(SUPER_ADMIN_PHONE);
                model.setUserName(SUPER_ADMIN_EMAIL);
                model.setAvatar("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1539995779,1680243852&fm=27&gp=0.jpg");
                model.setRole(UserRole.SUPER_ADMIN.getCode());
                model.setDescription("这是超级管理员账号");

                userRepository.insertUser(model);
            }
        });
    }

}
