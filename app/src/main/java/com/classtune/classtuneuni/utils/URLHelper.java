/**
 * 
 */
package com.classtune.classtuneuni.utils;

public class URLHelper {


    public URLHelper() {
	}
	public static final String BASE_URL= "http://edoozz.com/";
	//public static final String BASE_URL= "http://192.168.3.43/";
	//public static final String BASE_URL= "http://uni.edoozz.com/";
	//public static final String BASE_URL = "http://192.168.0.68/edoozz/";
	//public static final String BASE_URL = "http://192.168.2.38/";
	public static final String SUB_URL = "masterapi/";

	public final static String ADD_FCM = "users/add_fcm";
	public final static String URL_LOGIN = "users/login";
	public final static String URL_PROFILE = "users/profile";
	public final static String GET_MENU = "users/get_menu";
	public final static String GET_LOGOUT = "users/logout";

	public final static String GET_UNIVERSITY = "users/search_university";
	public static final String URL_REGISTER = "users/register";
	public static final String ADD_PHOTO = "users/add_photo";
	public static final String UPDATE_USER = "users/update";

	public static final String TEACHER_NOTICE_LIST = "notice_api/notice_list";
	public static final String TEACHER_NOTICE_TYPE = "notice_api/offered_sections";
	public static final String NOTICE_ADD = "notice_api/add";
	public static final String TEACHER_NOTICE_DETAILS = "notice_api/view";
	public static final String TEACHER_NOTICE_DELETE = "notice_api/delete";

	public static final String STUDENT_NOTICE_LIST = "notice_api/student_notice";
	public static final String STUDENT_NOTICE_DETAILS = "notice_api/student_view_notice";


	public static final String URL_ADD_COURSE = "course_api/add";
	public static final String TEACHER_COURSE_LIST = "course_api/course_list";
	public static final String OFFERED_COURSE_DETAILS ="course_api/view_offered_course" ;
	public static final String OFFERED_COURSE_SECTION_ADD = "course_api/add_section";
	public static final String OFFERED_COURSE_SECTION_STUDENT ="course_api/enrolled_students" ;
	public static final String URL_OFFER_COURSE = "course_api/add_course_offer";
	public static final String ADD_SECTION ="course_api/add_section" ;
	public static final String SECTION_LIST = "course_api/sections";
	public static final String ALL_COURSE_LIST = "course_api/all_course";
	public static final String COURSE_VIEW = "course_api/view_course";
	public static final String SINGLE_TEACHER_ALL_COURSE_LIST = "course_api/all_course";
	public static final String COURSE_REQUEST ="course_api/interested" ;


	public static final String ASSIGNMENT_LIST = "assignment_api/assignment_list";
	public static final String ASSIGNMENT_SUBMITED_LIST = "assignment_api/view";
	public static final String OFFERED_SECTION_LIST = "assignment_api/offered_sections";
	//public static final String OFFERED_COURSE_LIST = "assignment_api/offered_courses";
    public static final String ASSIGNMENT_ADD ="assignment_api/add" ;

	public static final String STUDENT_ASSIGNMENT_LIST = "assignment_api/student_course_assignments";


	public static final String SECTION_STUDENT_LIST = "attendance_api/enrolled_students";
	public static final String STUDENT_ATTENDANCE = "attendance_api/student";

	public static final String STUDENT_CLASS_SCHEDULE = "routine_api/course_routine";
	public static final String STUDENT_ALL_CLASS_SCHEDULE = "routine_api/student_routine_all";



	public static final String STUDENT_SECTION_LIST = "course_api/student_courses";
	public static final String STUDENT_ENROLL = "course_api/student_enroll";


	public static final String STUDENT_EXAM_LIST = "exam_api/student_course_exams";

	public static final String STUDENT_ASSIGNMENT_DETAILS = "assignment_api/student_view_assignment";

	public static final String STUDENT_SUBJECT_RESULT = "exam_api/student_course_result";

	public static final String STUDENT_COURSE_RESOURCE = "course_api/course_materials";
	public static final String SINGLE_RESOURCE = "course_api/course_material_single";



	public static final String STUDENT_PROFILE = "exam_api/student_profile";

	public static final String STUDENT_COURSE_MESSAGE = "course_api/course_discussion";
	public static final String STUDENT_SEND_MESSAGE = "course_api/send_message";

	//public static final String STUDENT_HOME = "dashboard_api/student_news_feed";
	public static final String STUDENT_HOME_HEADER= "dashboard_api/student_home_top";

	public static final String STUDENT_HOME = "dashboard_api/student_home";

	public static final String STUDENT_NOTIFICATION_LIST = "notification_api/notification_list";
	public static final String STUDENT_NOTIFICATION_COUNT = "notification_api/notification_count";

	public static final String REGISTRATION_VERIFICATION = "users/verify_email_code";
	public static final String RESEND_CODE = "users/again_verification_email";
	public static final String STUDENT_QUIZ_LIST = "quiz_api/quiz_list";
	public static final String STUDENT_QUIZ_QUESTION_LIST = "quiz_api/get_quiz_questions";
	public static final String STUDENT_QUIZ_SUBMIT = "quiz_api/save_quiz_answers";
	public static final String STUDENT_QUIZ_START = "quiz_api/start_quiz";
	public static final String STUDENT_QUIZ_RESULT = "quiz_api/get_quiz_results";



//teacher
public static final String OFFERED_COURSE_LIST = "course_api/course_offers";
	public static final String TEACHER_EXAM_LIST = "exam_api/exam_list";
	public static final String EXAM_DETAILS ="exam_api/student_exam_single" ;

	public static final String ATTENDANCE_STUDENT_LIST ="attendance_api/enrolled_students" ;
	public static final String SUBJECT_EXAM_POLICY = "exam_api/assessment_policy";
	public static final String TAKE_ATTENDANCE = "attendance_api/take_attendance";

	public static final String STUDENT_ATTENDANCE_REPORT = "attendance_api/students_report";

	public final static String URL_FORGET_PASSWORD = "users/reset_password_api";



	
}
