package com.example.quiz.config;

import com.example.quiz.models.Answer;
import com.example.quiz.models.Question;
import com.example.quiz.models.Role;
import com.example.quiz.models.Subject;
import com.example.quiz.models.User;
import com.example.quiz.repos.AnswerRepo;
import com.example.quiz.repos.QuestionRepo;
import com.example.quiz.repos.RoleRepository;
import com.example.quiz.repos.SubjectRepo;
import com.example.quiz.repos.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final SubjectRepo subjectRepo;
    private final QuestionRepo questionRepo;
    private final AnswerRepo answerRepo;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RoleRepository roleRepository, UserRepository userRepository, SubjectRepo subjectRepo,
            QuestionRepo questionRepo, AnswerRepo answerRepo, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.subjectRepo = subjectRepo;
        this.questionRepo = questionRepo;
        this.answerRepo = answerRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        Role userRole = getOrCreateRole("ROLE_USER");
        Role adminRole = getOrCreateRole("ROLE_ADMIN");

        createUserIfMissing("student", "student123", userRole);
        createUserIfMissing("admin", "admin123", userRole, adminRole);

        if (subjectRepo.count() == 0) {
            createSpringQuiz();
            createJavaQuiz();
        }
    }

    private Role getOrCreateRole(String name) {
        Role role = roleRepository.findByName(name);
        if (role != null) {
            return role;
        }

        Role newRole = new Role();
        newRole.setName(name);
        return roleRepository.save(newRole);
    }

    private void createUserIfMissing(String username, String password, Role... roles) {
        if (userRepository.findByUsername(username) != null) {
            return;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(Arrays.asList(roles));
        userRepository.save(user);
    }

    private void createSpringQuiz() {
        Subject subject = subjectRepo.save(new Subject("Spring Boot basics", true));

        Question beanQuestion = questionRepo.save(new Question("What creates and manages Spring beans?", 2, subject));
        createAnswers(beanQuestion,
                new Answer("Spring IoC container", true, beanQuestion),
                new Answer("JVM garbage collector", false, beanQuestion),
                new Answer("Maven compiler plugin", false, beanQuestion));

        Question controllerQuestion = questionRepo.save(new Question("Which annotation marks a REST controller?", 2, subject));
        createAnswers(controllerQuestion,
                new Answer("@RestController", true, controllerQuestion),
                new Answer("@Entity", false, controllerQuestion),
                new Answer("@RepositoryBean", false, controllerQuestion));
    }

    private void createJavaQuiz() {
        Subject subject = subjectRepo.save(new Subject("Java core", true));

        Question inheritanceQuestion = questionRepo.save(new Question("Which keyword is used for inheritance in Java?", 1, subject));
        createAnswers(inheritanceQuestion,
                new Answer("extends", true, inheritanceQuestion),
                new Answer("implements class", false, inheritanceQuestion),
                new Answer("inherits", false, inheritanceQuestion));

        Question collectionQuestion = questionRepo.save(new Question("Which collection stores unique values?", 1, subject));
        createAnswers(collectionQuestion,
                new Answer("Set", true, collectionQuestion),
                new Answer("List", false, collectionQuestion),
                new Answer("Queue", false, collectionQuestion));
    }

    private void createAnswers(Question question, Answer... answers) {
        Arrays.stream(answers).forEach(answer -> {
            answer.setQuestion(question);
            answerRepo.save(answer);
        });
    }
}
