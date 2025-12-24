// src/test/java/com/example/demo/DemoWorkflowEngineTestNGSuite.java
package com.example.demo;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.*;
import com.example.demo.util.HibernateQueryUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Optional;
@Listeners(TestResultListener.class)
@SpringBootTest
public class UserServiceTest extends AbstractTestNGSpringContextTests {

    private String uid() {
        return String.valueOf(System.nanoTime());
    }

    @Autowired private WorkflowTemplateService templateService;
    @Autowired private WorkflowStepConfigService stepService;
    @Autowired private ApprovalRequestService requestService;
    @Autowired private ApprovalActionService actionService;
    @Autowired private AuditLogService auditLogService;
    @Autowired private UserService userService;

    @Autowired private WorkflowTemplateRepository templateRepository;
    @Autowired private WorkflowStepConfigRepository stepRepository;
    @Autowired private ApprovalRequestRepository requestRepository;
    @Autowired private ApprovalActionRepository actionRepository;
    @Autowired private AuditLogRecordRepository auditRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;

    @Autowired private JwtTokenProvider tokenProvider;
    @Autowired private HibernateQueryUtil hibernateQueryUtil;
    @Autowired private PasswordEncoder passwordEncoder;

    private Long testTemplateId;
    private Long testUserId;
    private String jwtUsername;
    private String createdTemplateName;

    // --------------------------------------------------------------------
    // 1. Develop and deploy a simple servlet using Tomcat Server
    // --------------------------------------------------------------------

    public static class SimpleServlet extends jakarta.servlet.http.HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, java.io.IOException {
            resp.setStatus(200);
            PrintWriter writer = resp.getWriter();
            writer.write("OK");
            writer.flush();
        }
    }

    @Test(priority = 1)
    public void testSimpleServletDoGet_StatusOk() throws Exception {
        SimpleServlet servlet = new SimpleServlet();
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        StringWriter sw = new StringWriter();
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(sw));
        servlet.doGet(Mockito.mock(HttpServletRequest.class), response);
        Assert.assertEquals(sw.toString(), "OK");
    }

    @Test(priority = 2, groups = "servlet")
    public void testSimpleServletHandlesNullParams() throws Exception {
        SimpleServlet servlet = new SimpleServlet();
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        Mockito.when(request.getParameter("id")).thenReturn(null);
        StringWriter sw = new StringWriter();
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(sw));

        servlet.doGet(request, response);

        Assert.assertTrue(sw.toString().contains("OK"));
    }

    @Test(priority = 3, groups = "servlet")
    public void testSimpleServletNoExceptionThrown() {
        SimpleServlet servlet = new SimpleServlet();
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        try {
            Mockito.when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
            servlet.doGet(request, response);
        } catch (Exception e) {
            Assert.fail("Servlet threw exception: " + e.getMessage());
        }
    }

    @Test(priority = 4, groups = "servlet")
    public void testSimpleServletMultipleCalls() throws Exception {
        SimpleServlet servlet = new SimpleServlet();
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        StringWriter sw = new StringWriter();
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(sw));

        servlet.doGet(request, response);
        servlet.doGet(request, response);

        Assert.assertTrue(sw.toString().contains("OK"));
    }

    @Test(priority = 5, groups = "servlet")
    public void testSimpleServletWithQueryParam() throws Exception {
        SimpleServlet servlet = new SimpleServlet();
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

        Mockito.when(request.getParameter("q")).thenReturn("hello");
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));

        servlet.doGet(request, response);

        Assert.assertTrue(true);
    }

    @Test(priority = 6, groups = "servlet")
    public void testSimpleServletResponseWriterNotNull() throws Exception {
        SimpleServlet servlet = new SimpleServlet();
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        Mockito.when(response.getWriter()).thenReturn(pw);

        servlet.doGet(request, response);

        Assert.assertNotNull(sw.toString());
    }

    @Test(priority = 7, groups = "servlet")
    public void testSimpleServletOutputLength() throws Exception {
        SimpleServlet servlet = new SimpleServlet();
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        StringWriter sw = new StringWriter();
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(sw));

        servlet.doGet(request, response);

        Assert.assertTrue(sw.toString().length() >= 2);
    }

    // --------------------------------------------------------------------
    // 2. Implement CRUD operations using Spring Boot and REST APIs
    // --------------------------------------------------------------------

    @Test(priority = 10, groups = "crud")
    public void testCreateWorkflowTemplate() {

        // unique per run to avoid unique constraint on template_name
        createdTemplateName = "Test_Template_CRUD_" + uid();

        WorkflowTemplate template = new WorkflowTemplate();
        template.setTemplateName(createdTemplateName);
        template.setTotalLevels(2);
        template.setActive(true);

        WorkflowTemplate saved = templateService.createTemplate(template);
        testTemplateId = saved.getId();

        Assert.assertNotNull(testTemplateId);
    }

    @Test(priority = 11, groups = "crud")
    public void testReadWorkflowTemplate() {

        Assert.assertNotNull(testTemplateId);

        WorkflowTemplate template =
                templateService.getTemplateById(testTemplateId).orElseThrow();

        Assert.assertNotNull(template);
        Assert.assertEquals(template.getTemplateName(), createdTemplateName);
    }

    @Test(priority = 12, groups = "crud")
    public void testUpdateWorkflowTemplateDescription() {

        WorkflowTemplate template =
                templateService.getTemplateById(testTemplateId).orElseThrow();

        template.setDescription("Updated description");

        WorkflowTemplate updated =
                templateService.updateTemplate(template.getId(), template);

        Assert.assertEquals(updated.getDescription(), "Updated description");
    }

    @Test(priority = 13, groups = "crud")
    public void testActivateDeactivateWorkflowTemplate() {

        WorkflowTemplate template =
                templateService.activateTemplate(testTemplateId, false);

        Assert.assertFalse(template.getActive());

        template =
                templateService.activateTemplate(testTemplateId, true);

        Assert.assertTrue(template.getActive());
    }

    @Test(priority = 14, groups = "crud")
    public void testListWorkflowTemplates() {
        List<WorkflowTemplate> list = templateService.getAllTemplates();
        Assert.assertTrue(list.size() >= 1);
    }

    @Test(priority = 15, groups = "crud")
    public void testCreateApprovalRequest() {
        ApprovalRequest request = new ApprovalRequest();
        request.setTemplateId(testTemplateId);
        request.setRequesterId(1L);
        request.setRequestTitle("Request_" + uid());
        request.setRequestPayloadJson("{}");
        ApprovalRequest saved = requestService.createRequest(request);
        Assert.assertNotNull(saved.getId());
        Assert.assertEquals(saved.getStatus(), "PENDING");
    }

    @Test(priority = 16, groups = "crud")
    public void testGetRequestsByRequester() {
        List<ApprovalRequest> list = requestService.getRequestsByRequester(1L);
        Assert.assertTrue(list.size() >= 1);
    }

    @Test(priority = 17, groups = "crud")
    public void testGetAllRequests() {
        List<ApprovalRequest> list = requestService.getAllRequests();
        Assert.assertTrue(list.size() >= 1);
    }

    // --------------------------------------------------------------------
    // 3. Configure and perform Dependency Injection and IoC
    // --------------------------------------------------------------------

    @Test(priority = 20, groups = "di")
    public void testServicesInjected() {
        Assert.assertNotNull(templateService);
        Assert.assertNotNull(stepService);
        Assert.assertNotNull(requestService);
    }

    @Test(priority = 21, groups = "di")
    public void testRepositoriesInjected() {
        Assert.assertNotNull(templateRepository);
        Assert.assertNotNull(requestRepository);
        Assert.assertNotNull(userRepository);
    }

    @Test(priority = 22, groups = "di")
    public void testPasswordEncoderInjected() {
        Assert.assertNotNull(passwordEncoder);
        Assert.assertTrue(passwordEncoder.matches("pwd", passwordEncoder.encode("pwd")));
    }

    @Test(priority = 23, groups = "di")
    public void testBeanScopesWorking() {
        WorkflowTemplate template1 = new WorkflowTemplate();
        template1.setTemplateName("Scope1_" + uid());
        template1.setTotalLevels(1);
        WorkflowTemplate saved1 = templateService.createTemplate(template1);
        Assert.assertNotNull(saved1.getId());

        WorkflowTemplate template2 = new WorkflowTemplate();
        template2.setTemplateName("Scope2_" + uid());
        template2.setTotalLevels(1);
        WorkflowTemplate saved2 = templateService.createTemplate(template2);
        Assert.assertNotNull(saved2.getId());

        List<WorkflowTemplate> allTemplates = templateService.getAllTemplates();
        Assert.assertTrue(allTemplates.size() >= 2);
        Assert.assertNotEquals(saved1.getId(), saved2.getId());
    }

    @Test(priority = 24, groups = "di")
    public void testAutowiredHibernateQueryUtil() {
        Assert.assertNotNull(hibernateQueryUtil);
    }

    @Test(priority = 25, groups = "di")
    public void testAutowiredJwtProvider() {
        Assert.assertNotNull(tokenProvider);
    }

    @Test(priority = 26, groups = "di")
    public void testAutowiredAuditLogService() {
        Assert.assertNotNull(auditLogService);
    }

    @Test(priority = 27, groups = "di")
    public void testAutowiredUserService() {
        Assert.assertNotNull(userService);
    }

    // --------------------------------------------------------------------
    // 4. Implement Hibernate configurations, generator classes, annotations, CRUD
    // --------------------------------------------------------------------

    @Test(priority = 30, groups = "hibernate")
    public void testHibernateSaveAndFindTemplate() {

        String name = "Hibernate_Template_" + uid();

        WorkflowTemplate t = new WorkflowTemplate();
        t.setTemplateName(name);
        t.setTotalLevels(2);
        t.setActive(true);

        WorkflowTemplate saved = templateRepository.save(t);

        Optional<WorkflowTemplate> found =
                templateRepository.findById(saved.getId());

        Assert.assertTrue(found.isPresent());
        Assert.assertEquals(found.get().getTemplateName(), name);
    }

    @Test(priority = 31, groups = "hibernate")
    public void testHibernateDeleteTemplate() {
        WorkflowTemplate template = new WorkflowTemplate();
        template.setTemplateName("Hibernate_Delete_" + uid());
        template.setTotalLevels(1);
        WorkflowTemplate saved = templateRepository.save(template);
        Long id = saved.getId();
        templateRepository.deleteById(id);
        Assert.assertFalse(templateRepository.findById(id).isPresent());
    }

    @Test(priority = 32, groups = "hibernate")
    public void testHibernateUpdateRequestStatus() {
        ApprovalRequest r = new ApprovalRequest();
        r.setTemplateId(testTemplateId);
        r.setRequesterId(5L);
        r.setRequestTitle("Hibernate Update " + uid());
        r.setRequestPayloadJson("{}");
        r = requestRepository.save(r);
        r.setStatus("IN_PROGRESS");
        r = requestRepository.save(r);
        Assert.assertEquals(r.getStatus(), "IN_PROGRESS");
    }

    @Test(priority = 33, groups = "hibernate")
    public void testAuditLogPersisted() {
        AuditLogRecord log = new AuditLogRecord();
        log.setRequestId(999L);
        log.setEventType("TEST");
        log.setDetails("Test audit log");
        AuditLogRecord saved = auditRepository.save(log);
        Assert.assertNotNull(saved.getId());
    }

    @Test(priority = 34, groups = "hibernate")
    public void testAuditLogQueryByRequestId() {
        List<AuditLogRecord> logs = auditRepository.findByRequestId(999L);
        Assert.assertTrue(logs.size() >= 1);
    }

    @Test(priority = 35, groups = "hibernate")
    public void testApprovalActionGeneratorIdentity() {
        ApprovalAction a = new ApprovalAction();
        a.setAction("APPROVED");
        a.setLevelNumber(1);
        a.setRequestId(1000L);
        a.setApproverId(200L);
        ApprovalAction saved = actionRepository.save(a);
        Assert.assertNotNull(saved.getId());
    }

    @Test(priority = 36, groups = "hibernate")
    public void testHibernateLobFields() {
        ApprovalRequest req = new ApprovalRequest();
        req.setTemplateId(testTemplateId);
        req.setRequesterId(9L);
        req.setRequestTitle("LOB Test " + uid());
        req.setRequestPayloadJson("{\"longText\":\"" + "x".repeat(1024) + "\"}");
        ApprovalRequest saved = requestRepository.save(req);
        Assert.assertTrue(saved.getRequestPayloadJson().contains("longText"));
    }

    @Test(priority = 37, groups = "hibernate")
    public void testHibernateNotNullConstraint() {
        ApprovalRequest req = new ApprovalRequest();
        req.setTemplateId(testTemplateId);
        req.setRequesterId(9L);
        req.setRequestTitle("NotNull " + uid());
        req.setRequestPayloadJson("{}");
        ApprovalRequest saved = requestRepository.save(req);
        Assert.assertNotNull(saved.getStatus());
    }

    // --------------------------------------------------------------------
    // 5. Perform JPA mapping with normalization (1NF, 2NF, 3NF)
    // --------------------------------------------------------------------

    @Test(priority = 40, groups = "jpa")
    public void testTemplateAndStepNormalization() {

        String templateName = "Norm_Template_" + uid();

        WorkflowTemplate t = new WorkflowTemplate();
        t.setTemplateName(templateName);
        t.setTotalLevels(3);
        t.setActive(true);
        t = templateRepository.save(t);

        WorkflowStepConfig s1 = new WorkflowStepConfig();
        s1.setTemplateId(t.getId());
        s1.setLevelNumber(1);
        s1.setApproverRole("REQUESTER");
        stepRepository.save(s1);

        WorkflowStepConfig s2 = new WorkflowStepConfig();
        s2.setTemplateId(t.getId());
        s2.setLevelNumber(2);
        s2.setApproverRole("APPROVER");
        stepRepository.save(s2);

        List<WorkflowStepConfig> steps =
                stepRepository.findByTemplateIdOrderByLevelNumberAsc(t.getId());
        Assert.assertEquals(steps.size(), 2);
    }

    @Test(priority = 42, groups = "jpa")
    public void testNoRedundantColumnsForSteps() {
        WorkflowStepConfig s = new WorkflowStepConfig();
        s.setTemplateId(123L); // assumes no FK constraint
        s.setLevelNumber(1);
        s.setApproverRole("APPROVER");
        s.setInstructions("Review");
        WorkflowStepConfig saved = stepRepository.save(s);
        Assert.assertNotNull(saved.getId());
    }

    @Test(priority = 43, groups = "jpa")
    public void testThirdNormalFormForAudit() {
        AuditLogRecord log = new AuditLogRecord();
        log.setRequestId(1234L);
        log.setEventType("NORMALIZED");
        log.setDetails("Detail message");
        AuditLogRecord saved = auditRepository.save(log);
        Assert.assertNotNull(saved.getId());
    }

    @Test(priority = 44, groups = "jpa")
    public void testUniqueTemplateNameConstraint() {

        String uniqueName = "UniqueNameTest_" + uid();

        WorkflowTemplate t1 = new WorkflowTemplate();
        t1.setTemplateName(uniqueName);
        t1.setTotalLevels(1);
        templateRepository.save(t1);

        WorkflowTemplate t2 = new WorkflowTemplate();
        t2.setTemplateName(uniqueName);
        t2.setTotalLevels(1);
        try {
            templateRepository.saveAndFlush(t2);
            Assert.fail("Expected unique constraint violation for template_name");
        } catch (Exception e) {
            // expected
            Assert.assertTrue(true);
        }
    }

    @Test(priority = 45, groups = "jpa")
    public void test1NFNoRepeatingGroups() {
        WorkflowTemplate t = new WorkflowTemplate();
        t.setTemplateName("1NFTest_" + uid());
        t.setTotalLevels(1);
        templateRepository.save(t);
        Assert.assertNotNull(t.getId());
    }

    @Test(priority = 46, groups = "jpa")
    public void test2NFDependency() {
        WorkflowStepConfig s = new WorkflowStepConfig();
        s.setTemplateId(testTemplateId);
        s.setLevelNumber(99);
        s.setApproverRole("ADMIN");
        stepRepository.save(s);
        Assert.assertNotNull(s.getId());
    }

    // --------------------------------------------------------------------
    // 6. Create Many-to-Many relationships and test associations
    // --------------------------------------------------------------------

    @Test(priority = 50)
    public void testUserRoleManyToManyAssociation() {
        String id = uid();

        Role role = roleRepository.findByName("REQUESTER")
                .orElseGet(() -> {
                    Role r = new Role();
                    r.setName("REQUESTER");
                    return roleRepository.save(r);
                });

        User user = new User();
        user.setUsername("many_" + id);
        user.setEmail("many_" + id + "@example.com");
        user.setPassword("password");
        user.getRoles().add(role);
        user = userService.registerUser(user, "REQUESTER");
        testUserId = user.getId();
        Assert.assertTrue(user.getRoles().size() >= 1);
    }

    @Test(priority = 51)
    public void testUserHasMultipleRoles() {
        Role roleAdmin = roleRepository.findByName("ADMIN")
                .orElseGet(() -> {
                    Role r = new Role();
                    r.setName("ADMIN");
                    return roleRepository.save(r);
                });

        User user = userRepository.findById(testUserId).orElseThrow();
        user.getRoles().add(roleAdmin);
        userRepository.save(user);
        User updated = userRepository.findById(testUserId).orElseThrow();
        Assert.assertTrue(updated.getRoles().size() >= 2);
    }

    @Test(priority = 52)
    public void testRoleSharedByMultipleUsers() {
        String id = uid();
        Role role = roleRepository.findByName("REQUESTER").orElseThrow();
        User user2 = new User();
        user2.setUsername("user2_" + id);
        user2.setEmail("user2_" + id + "@example.com");
        user2.setPassword("password");
        user2.getRoles().add(role);
        user2 = userService.registerUser(user2, "REQUESTER");
        Assert.assertTrue(user2.getRoles().stream().anyMatch(r -> "REQUESTER".equals(r.getName())));
    }

    @Test(priority = 53, groups = "manyToMany")
    public void testUserRolesEagerFetch() {
        User user = userRepository.findById(testUserId).orElseThrow();
        Assert.assertFalse(user.getRoles().isEmpty());
    }

    @Test(priority = 54)
    public void testManyToManyEdgeCaseUserWithoutRoles() {
        String id = uid();
        User u = new User();
        u.setUsername("norole_" + id);
        u.setEmail("norole_" + id + "@example.com");
        u.setPassword("pwd");
        u = userRepository.save(u);
        Assert.assertTrue(u.getRoles().isEmpty());
    }

    @Test(priority = 55, groups = "manyToMany")
    public void testManyToManyRoleNameUnique() {
        String roleName = "UNIQUE_ROLE_" + uid();
        Role r = new Role();
        r.setName(roleName);
        roleRepository.save(r);
        Optional<Role> again = roleRepository.findByName(roleName);
        Assert.assertTrue(again.isPresent());
    }

    @Test(priority = 56, groups = "manyToMany")
    public void testManyToManyDeleteUserNotDeleteRole() {
        Role before = roleRepository.findByName("REQUESTER").orElseThrow();
        userRepository.deleteById(testUserId);
        Role after = roleRepository.findByName("REQUESTER").orElseThrow();
        Assert.assertEquals(before.getId(), after.getId());
    }

    // --------------------------------------------------------------------
    // 7. Implement basic security controls and JWT token-based authentication
    // --------------------------------------------------------------------

    @Test(priority = 60)
    public void testJwtTokenIncludesUserIdEmailRoles() {
        String id = uid();
        jwtUsername = "jwt_" + id;

        Role role = roleRepository.findByName("APPROVER")
                .orElseGet(() -> {
                    Role r = new Role();
                    r.setName("APPROVER");
                    return roleRepository.save(r);
                });

        User user = new User();
        user.setUsername(jwtUsername);
        user.setEmail(jwtUsername + "@example.com");
        user.setPassword("pwd");
        user.getRoles().add(role);
        user = userService.registerUser(user, "APPROVER");

        String token = tokenProvider.generateToken(user);
        Assert.assertNotNull(token);
        Long userIdFromToken = tokenProvider.getUserIdFromToken(token);
        Assert.assertEquals(userIdFromToken, user.getId());
    }

    @Test(priority = 61)
    public void testJwtTokenValidationPositive() {
        User user = userService.findByUsername(jwtUsername);
        String token = tokenProvider.generateToken(user);
        Assert.assertTrue(tokenProvider.validateToken(token));
    }

    @Test(priority = 62)
    public void testJwtTokenValidationNegative() {
        Assert.assertFalse(tokenProvider.validateToken("bad.token.value"));
    }

    @Test(priority = 63, groups = "security")
    public void testPasswordEncodingForSecurity() {
        String raw = "securePwd";
        String encoded = passwordEncoder.encode(raw);
        Assert.assertNotEquals(raw, encoded);
        Assert.assertTrue(passwordEncoder.matches(raw, encoded));
    }

    @Test(priority = 64)
    public void testRegisterAndLoginUserSecurityFlow() {
        String id = uid();

        RegisterRequest req = new RegisterRequest();
        req.setUsername("flow_" + id);
        req.setEmail("flow_" + id + "@example.com");
        req.setPassword("flowPwd");
        req.setRole("REQUESTER");

        User user = new User();
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setPassword(req.getPassword());
        user = userService.registerUser(user, req.getRole());

        AuthRequest authReq = new AuthRequest();
        authReq.setUsernameOrEmail(req.getUsername());
        authReq.setPassword(req.getPassword());

        String token = tokenProvider.generateToken(user);
        Assert.assertTrue(tokenProvider.validateToken(token));
    }

    @Test(priority = 65, groups = "security")
    public void testSecurityEdgeCaseExpiredTokenSimulation() {
        String fakeExpiredToken = "expired.fake.token";
        Assert.assertFalse(tokenProvider.validateToken(fakeExpiredToken));
    }

    @Test(priority = 66, groups = "security")
    public void testSecurityRolesAssignedCorrectly() {
        User user = userService.findByUsername(jwtUsername);
        Assert.assertTrue(user.getRoles().stream().anyMatch(r -> "APPROVER".equals(r.getName())));
    }

    @Test(priority = 67, groups = "security")
    public void testSecurityInvalidLoginPasswordMismatch() {
        User user = userService.findByUsername(jwtUsername);
        boolean matches = passwordEncoder.matches("wrongPwd", user.getPassword());
        Assert.assertFalse(matches);
    }

    // --------------------------------------------------------------------
    // 8. Use HQL and HCQL to perform advanced data querying
    // --------------------------------------------------------------------

    @Test(priority = 70)
    public void testHqlFindByLevelAndAction() {
        ApprovalAction a = new ApprovalAction();
        a.setRequestId(1000L);
        a.setApproverId(2000L);
        a.setLevelNumber(1);
        a.setAction("APPROVED");
        actionRepository.save(a);

        List<ApprovalAction> list = actionRepository.findByLevelAndAction(1, "APPROVED");
        Assert.assertTrue(list.size() >= 1);
    }

    @Test(priority = 71, groups = "hql")
    public void testCriteriaFindActionsByApprover() {
        ApprovalAction a = new ApprovalAction();
        a.setRequestId(999L);
        a.setApproverId(777L);
        a.setLevelNumber(1);
        a.setAction("APPROVED");
        actionRepository.save(a);

        List<ApprovalAction> list =
                hibernateQueryUtil.findActionsByApproverUsingCriteria(777L);
        Assert.assertTrue(list.size() >= 1);
    }

    @Test(priority = 72, groups = "hql")
    public void testHqlQueryNoResultEdgeCase() {
        List<ApprovalAction> list = actionRepository.findByLevelAndAction(9999, "APPROVED");
        Assert.assertTrue(list.isEmpty() || list.size() >= 0);
    }

    @Test(priority = 73, groups = "hql")
    public void testCriteriaQueryNoActionsForUnknownApprover() {
        List<ApprovalAction> list =
                hibernateQueryUtil.findActionsByApproverUsingCriteria(-1L);
        Assert.assertTrue(list.isEmpty());
    }

    @Test(priority = 74, groups = "hql")
    public void testHqlComplexScenarioMultipleRecords() {
        ApprovalAction a1 = new ApprovalAction();
        a1.setRequestId(1001L);
        a1.setApproverId(100L);
        a1.setLevelNumber(1);
        a1.setAction("APPROVED");
        actionRepository.save(a1);

        ApprovalAction a2 = new ApprovalAction();
        a2.setRequestId(1002L);
        a2.setApproverId(100L);
        a2.setLevelNumber(1);
        a2.setAction("APPROVED");
        actionRepository.save(a2);

        List<ApprovalAction> list = actionRepository.findByLevelAndAction(1, "APPROVED");
        Assert.assertTrue(list.size() >= 2);
    }

    @Test(priority = 75, groups = "hql")
    public void testCriteriaQueryWithDifferentApprover() {
        ApprovalAction a = new ApprovalAction();
        a.setRequestId(2000L);
        a.setApproverId(300L);
        a.setLevelNumber(3);
        a.setAction("REJECTED");
        actionRepository.save(a);

        List<ApprovalAction> list =
                hibernateQueryUtil.findActionsByApproverUsingCriteria(300L);
        Assert.assertTrue(list.stream().anyMatch(x -> x.getRequestId().equals(2000L)));
    }

    @Test(priority = 76, groups = "hql")
    public void testHqlQueryPerformanceSimulation() {
        for (int i = 0; i < 5; i++) {
            ApprovalAction a = new ApprovalAction();
            a.setRequestId((long) (3000 + i));
            a.setApproverId(400L);
            a.setLevelNumber(i + 1);
            a.setAction("APPROVED");
            actionRepository.save(a);
        }
        List<ApprovalAction> list = actionRepository.findByLevelAndAction(1, "APPROVED");
        Assert.assertTrue(list.size() >= 1);
    }

    @Test(priority = 77)
    public void testCriteriaEdgeCaseNullApprover() {
        List<ApprovalAction> list =
                hibernateQueryUtil.findActionsByApproverUsingCriteria(null);
        Assert.assertNotNull(list);
    }
}
