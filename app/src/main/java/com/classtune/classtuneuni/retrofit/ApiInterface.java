package com.classtune.classtuneuni.retrofit;


import com.classtune.classtuneuni.assignment.AssignmentSectionResponse;
import com.classtune.classtuneuni.assignment.AssignmentSubmitedListResponse;
import com.classtune.classtuneuni.assignment.Status;
import com.classtune.classtuneuni.assignment.TeacherAssignmentResponse;
import com.classtune.classtuneuni.attendance.AttendanceReportResponse;
import com.classtune.classtuneuni.attendance.StudentAttendanceResponse;
import com.classtune.classtuneuni.attendance.StudentListAttenResponse;
import com.classtune.classtuneuni.class_schedule.StClsScheduleResponse;
import com.classtune.classtuneuni.course_resonse.CorsSecStudentResponse;
import com.classtune.classtuneuni.course_resonse.CourseDetailsResponse;
import com.classtune.classtuneuni.course_resonse.CourseListResponse;
import com.classtune.classtuneuni.course_resonse.CourseOfferSectionResponse;
import com.classtune.classtuneuni.course_resonse.OfferedCourseResponse;
import com.classtune.classtuneuni.enroll.StEnrollResponse;
import com.classtune.classtuneuni.exam.ExamPolicyResponse;
import com.classtune.classtuneuni.exam.ExamResponse;
import com.classtune.classtuneuni.home.StHomeHeaderRespons;
import com.classtune.classtuneuni.home.StHomeRespons;
import com.classtune.classtuneuni.message.StCourseMsgResponse;
import com.classtune.classtuneuni.message.StSendMsgResponse;
import com.classtune.classtuneuni.model.CommonStatus;
import com.classtune.classtuneuni.model.LoginApiModel;
import com.classtune.classtuneuni.model.UniversityModel;
import com.classtune.classtuneuni.notice.NoticeDetailsResponse;
import com.classtune.classtuneuni.notice.StNoticeResonse;
import com.classtune.classtuneuni.profile.StProfileRsponse;
import com.classtune.classtuneuni.resource.ResourceResponse;
import com.classtune.classtuneuni.response.NoticeOfferResponse;
import com.classtune.classtuneuni.response.NoticeResonse;
import com.classtune.classtuneuni.response.RegisTrationResponse;
import com.classtune.classtuneuni.response.StSectionListResponse;
import com.classtune.classtuneuni.result.StCourseResultResponse;
import com.classtune.classtuneuni.utils.URLHelper;
import com.google.gson.JsonElement;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;


//import io.reactivex.Observable;


/**
 * Created by RR on 27-Dec-17.
 */

public interface ApiInterface {


    @FormUrlEncoded
    @POST(URLHelper.ADD_FCM)
//    Observable<Response<List<LoginResponseModel>>> userLogin(@Field("username") String userId, @Field("password") String password);
    Observable<Response<JsonElement>> addFcm(@Field("fcm_id") String fcm_id);

    @FormUrlEncoded
    @POST(URLHelper.URL_LOGIN)
//    Observable<Response<List<LoginResponseModel>>> userLogin(@Field("username") String userId, @Field("password") String password);
    Observable<Response<LoginApiModel>> userLogin(@Field("email") String userId, @Field("password") String password, @Field("fcm_id") String gcm_id);

    @FormUrlEncoded
    @POST(URLHelper.GET_LOGOUT)
    Observable<Response<JsonElement>> getLogout(@Field("api_key") String api_key, @Field("fcm_id") String fcm_id);

    @FormUrlEncoded
    @POST(URLHelper.GET_UNIVERSITY)
    Observable<Response<UniversityModel>> getUniversity(@Field("search") String search);

//    Observable<Response<UniversityModel>> getUniversity(@Field("fcm_id") String fcm_id, @Field("search") String search);


    @FormUrlEncoded
    @POST(URLHelper.URL_REGISTER)
//    Observable<Response<List<LoginResponseModel>>> userLogin(@Field("username") String userId, @Field("password") String password);
    Observable<Response<RegisTrationResponse>> userRegWithCode(@Field("email") String userId, @Field("password") String password, @Field("confirm_password") String confirm_password, @Field("name") String name, @Field("user_type") String user_type, @Field("university_id") String university_id, @Field("fcm_id") String fcm_id);

    @FormUrlEncoded
    @POST(URLHelper.URL_REGISTER)
//    Observable<Response<List<LoginResponseModel>>> userLogin(@Field("username") String userId, @Field("password") String password);
    Observable<Response<RegisTrationResponse>> studentRegistrationName(@Field("email") String userId, @Field("password") String password, @Field("confirm_password") String confirm_password, @Field("name") String name, @Field("user_type") String user_type, @Field("student_id") String student_id, @Field("fcm_id") String fcm_id, @Field("mobile") String mobile,  @Field("university_name") String university_name);

    @FormUrlEncoded
    @POST(URLHelper.URL_REGISTER)
//    Observable<Response<List<LoginResponseModel>>> userLogin(@Field("username") String userId, @Field("password") String password);
    Observable<Response<RegisTrationResponse>> studentRegistration(@Field("email") String userId, @Field("password") String password, @Field("confirm_password") String confirm_password, @Field("name") String name, @Field("user_type") String user_type, @Field("student_id") String student_id, @Field("fcm_id") String fcm_id, @Field("mobile") String mobile,  @Field("university_id") String university_id);


    @FormUrlEncoded
    @POST(URLHelper.URL_REGISTER)
//    Observable<Response<List<LoginResponseModel>>> userLogin(@Field("username") String userId, @Field("password") String password);
    Observable<Response<RegisTrationResponse>> userRegWithName(@Field("email") String userId, @Field("password") String password, @Field("confirm_password") String confirm_password, @Field("name") String name,  @Field("user_type") String user_type, @Field("university_name") String university_id, @Field("fcm_id") String fcm_id);
    @Multipart
    @POST(URLHelper.ADD_PHOTO)
        //Observable<Response<JsonElement>> getTaskAssign(@Body RequestBody file);
    Observable<Response<JsonElement>> userImageUpload(@Part MultipartBody.Part attachment_file_name, @Part("api_key") RequestBody api_key );

    @FormUrlEncoded
    @POST(URLHelper.TEACHER_NOTICE_LIST)
   // Observable<Response<JsonElement>> getTeacherNitceList(@Field("api_key") String api_key);

    Observable<Response<NoticeResonse>> getTeacherNitceList(@Field("api_key") String api_key);

    @FormUrlEncoded
    @POST(URLHelper.TEACHER_NOTICE_TYPE)
    Observable<Response<NoticeOfferResponse>> getTeacherNitceType(@Field("api_key") String api_key);

    @FormUrlEncoded
    @POST(URLHelper.NOTICE_ADD)
    Observable<Response<JsonElement>> addNotice(@Field("title") String title, @Field("descriptions") String descriptions, @Field("sections") String sections, @Field("api_key") String api_key);

    @FormUrlEncoded
    @POST(URLHelper.TEACHER_NOTICE_DETAILS)
    Observable<Response<NoticeDetailsResponse>> getNoticeDetails(@Field("api_key") String api_key, @Field("notice_id") String notice_id);

    @FormUrlEncoded
    @POST(URLHelper.TEACHER_NOTICE_DELETE)
    Observable<Response<JsonElement>> deleteNotice(@Field("api_key") String api_key, @Field("notice_id") String notice_id);

    @FormUrlEncoded
    @POST(URLHelper.URL_ADD_COURSE)
    Observable<Response<CommonStatus>> addCourse(@Field("name") String name, @Field("course_code") String course_code, @Field("credit_point") String credit_point, @Field("api_key") String api_key);

    @FormUrlEncoded
    @POST(URLHelper.TEACHER_COURSE_LIST)
    Observable<Response<CourseListResponse>> getTeacherCourseList(@Field("api_key") String api_key);

    @FormUrlEncoded
    @POST(URLHelper.OFFERED_COURSE_DETAILS)
    Observable<Response<OfferedCourseResponse>> OfferedCourseDetails(@Field("api_key") String api_key, @Field("course_id") String course_id);

    @FormUrlEncoded
    @POST(URLHelper.OFFERED_COURSE_SECTION_ADD)
    Observable<Response<JsonElement>> OfferedCourseSectionAdd(@Field("api_key") String api_key, @Field("course_offers_id") String course_offers_id, @Field("name") String name);

    @FormUrlEncoded
    @POST(URLHelper.OFFERED_COURSE_SECTION_STUDENT)
    Observable<Response<CorsSecStudentResponse>> getCourseStudentSection(@Field("api_key") String api_key, @Field("course_offer_sections_id") String course_offer_sections_id);

    @FormUrlEncoded
    @POST(URLHelper.ASSIGNMENT_LIST)
    Observable<Response<TeacherAssignmentResponse>> getAssignmentList(@Field("api_key") String api_key);

    @FormUrlEncoded
    @POST(URLHelper.URL_OFFER_COURSE)
    Observable<Response<CommonStatus>> offerCourse(@Field("course_id") String course_id, @Field("start_date") String start_date, @Field("end_date") String end_date, @Field("sections") String sections, @Field("api_key") String api_key);

    @FormUrlEncoded
    @POST(URLHelper.ADD_SECTION)
    Observable<Response<CommonStatus>> addSection(@Field("api_key") String api_key, @Field("name") String name);

    @FormUrlEncoded
    @POST(URLHelper.SECTION_LIST)
    Observable<Response<CourseOfferSectionResponse>> getSectionList(@Field("api_key") String api_key);

    @FormUrlEncoded
    @POST(URLHelper.ASSIGNMENT_SUBMITED_LIST)
    Observable<Response<AssignmentSubmitedListResponse>> getAssigSubmitedStudentList(@Field("api_key") String api_key, @Field("assignment_id") String assignment_id);

    @FormUrlEncoded
    @POST(URLHelper.ASSIGNMENT_LIST)
    Observable<Response<TeacherAssignmentResponse>> getAssignmentCourses(@Field("api_key") String api_key);

    @FormUrlEncoded
    @POST(URLHelper.OFFERED_SECTION_LIST)
    Observable<Response<AssignmentSectionResponse>> getOfferedSectionList(@Field("api_key") String api_key);



    @FormUrlEncoded
    @POST(URLHelper.SECTION_STUDENT_LIST)
    //Observable<Response<JsonElement>> getSectionStudentList(@Field("api_key") String api_key, @Field("course_offer_sections_id") String course_offer_sections_id);

    Observable<Response<CorsSecStudentResponse>> getSectionStudentList(@Field("api_key") String api_key, @Field("course_offer_sections_id") String course_offer_sections_id, @Field("date") String date);

    //    @POST(URLHelper.ASSIGNMENT_ADD)
//        //Observable<Response<JsonElement>> getTaskAssign(@Body RequestBody file);
//    Observable<Response<Status>> getTaskAssign(@Body MultipartBody file );
    @Multipart
    @POST(URLHelper.ASSIGNMENT_ADD)
//Observable<Response<JsonElement>> getTaskAssign(@Body RequestBody file);
    Observable<Response<JsonElement>> getTaskAssign(@Part MultipartBody.Part[] attachment_file_name, @Part("api_key") RequestBody api_key, @Part("course_id") RequestBody course_id, @Part("course_offer_section_id") RequestBody course_offer_section_id,@Part("assessment") RequestBody assessment,@Part("max_marks") RequestBody max_marks,@Part("title") RequestBody title,@Part("description") RequestBody description,@Part("due_date") RequestBody due_date);

    @FormUrlEncoded
    @POST(URLHelper.STUDENT_NOTICE_LIST)
    Observable<Response<NoticeResonse>> getStudentNitceList(@Field("api_key") String api_key);

    @FormUrlEncoded
    @POST(URLHelper.STUDENT_NOTICE_DETAILS)
    Observable<Response<NoticeDetailsResponse>> getStNoticeDetails(@Field("api_key") String api_key, @Field("notice_id") String notice_id);

    @FormUrlEncoded
    @POST(URLHelper.STUDENT_ATTENDANCE)
    Observable<Response<StudentAttendanceResponse>> getStudentAttendance(@Field("api_key") String api_key, @Field("course_offer_section_id") String course_offer_section_id);

    @FormUrlEncoded
    @POST(URLHelper.STUDENT_CLASS_SCHEDULE)
    Observable<Response<StClsScheduleResponse>> getStClassSchedule(@Field("api_key") String api_key, @Field("course_offer_section_id") String course_offer_section_id);

    @FormUrlEncoded
    @POST(URLHelper.STUDENT_ALL_CLASS_SCHEDULE)
    Observable<Response<StClsScheduleResponse>> getStAllClassSchedule(@Field("api_key") String api_key);


    @FormUrlEncoded
    @POST(URLHelper.STUDENT_SECTION_LIST)
    Observable<Response<StSectionListResponse>> getStSectionList(@Field("api_key") String api_key);

    @FormUrlEncoded
    @POST(URLHelper.STUDENT_ENROLL)
    Observable<Response<StEnrollResponse>> getStEnroll(@Field("api_key") String api_key, @Field("enroll_code") String enroll_code);

    @FormUrlEncoded
    @POST(URLHelper.STUDENT_ASSIGNMENT_LIST)
    Observable<Response<TeacherAssignmentResponse>> getStAssignmentList(@Field("api_key") String api_key, @Field("course_offer_section_id") String course_offer_section_id,  @Field("page") int page);

    @FormUrlEncoded
    @POST(URLHelper.STUDENT_EXAM_LIST)
    Observable<Response<ExamResponse>> getStExamList(@Field("api_key") String api_key, @Field("course_offer_section_id") String course_offer_section_id);

    @FormUrlEncoded
    @POST(URLHelper.STUDENT_ASSIGNMENT_DETAILS)
    Observable<Response<TeacherAssignmentResponse>> getStAssignmentDetails(@Field("api_key") String api_key, @Field("assignment_id") String assignment_id);

    @FormUrlEncoded
    @POST(URLHelper.STUDENT_SUBJECT_RESULT)
    Observable<Response<StCourseResultResponse>> getSubjectResult(@Field("api_key") String api_key, @Field("course_offer_section_id") String course_offer_section_id);


    @FormUrlEncoded
    @POST(URLHelper.STUDENT_COURSE_RESOURCE)
    Observable<Response<ResourceResponse>> getSubjectResource(@Field("api_key") String api_key, @Field("course_offer_sections_id") String course_offer_section_id, @Field("page") String page);

    @FormUrlEncoded
    @POST(URLHelper.STUDENT_PROFILE)
    Observable<Response<StProfileRsponse>> getStProfile(@Field("api_key") String api_key);
    //Observable<Response<JsonElement>> getStProfile(@Field("api_key") String api_key);


    @FormUrlEncoded
    @POST(URLHelper.STUDENT_COURSE_MESSAGE)
    Observable<Response<StCourseMsgResponse>> getSubjectMessage(@Field("api_key") String api_key, @Field("course_offer_sections_id") String course_offer_section_id, @Field("page") int page);

    @FormUrlEncoded
    @POST(URLHelper.STUDENT_SEND_MESSAGE)
    Observable<Response<StSendMsgResponse>> stSendMessage(@Field("api_key") String api_key, @Field("course_offer_section_id") String course_offer_section_id, @Field("message") String message);

    @Multipart
    @POST(URLHelper.STUDENT_SEND_MESSAGE)
    Observable<Response<StSendMsgResponse>> stSentPhot(@Part MultipartBody.Part attachment_file_name, @Part("course_offer_section_id") RequestBody course_offer_section_id ,@Part("api_key") RequestBody api_key);

//    @FormUrlEncoded
//    @POST(URLHelper.STUDENT_HOME)
//    Observable<Response<StHomeRespons>> getStHome(@Field("api_key") String api_key, @Field("page") int page);

    @FormUrlEncoded
    @POST(URLHelper.STUDENT_HOME)
    Observable<Response<StHomeRespons>> getStHome(@Field("api_key") String api_key);

    @FormUrlEncoded
    @POST(URLHelper.STUDENT_HOME_HEADER)
    Observable<Response<StHomeHeaderRespons>> getStHomeHeader(@Field("api_key") String api_key);


    //teacher

//    @FormUrlEncoded
//    @POST(URLHelper.OFFERED_COURSE_LIST)
//    Observable<Response<CourseOfferSectionResponse>> getOfferedCourseList(@Field("api_key") String api_key);

    @FormUrlEncoded
    @POST(URLHelper.TEACHER_EXAM_LIST)
    Observable<Response<ExamResponse>> getTeacherExamList(@Field("api_key") String api_key, @Field("course_offer_section_id") String course_offer_section_id);


//    @FormUrlEncoded
//    @POST(Api.user_create_monthly_Plan)
//    Observable<Response<ResponseBody>> monthly_plan(@Query("user_id") int userId,
//                                                    @Query("month") String month,
//                                                    @Query("api_token") String token,
//                                                    @Field("plan_list[]") List<String> plan_list);

//    @FormUrlEncoded
//    @POST(URLHelper.ATTENDANCE_STUDENT_LIST)
//    Observable<Response<StudentListAttenResponse>> getSecStudents(@Field("api_key") String api_key, @Field("course_offer_sections_id") String course_offer_sections_id);

    @FormUrlEncoded
    @POST(URLHelper.SUBJECT_EXAM_POLICY)
    Observable<Response<ExamPolicyResponse>> getSubjectpolicy(@Field("api_key") String api_key, @Field("course_offer_section_id") String course_offer_section_id);


    @FormUrlEncoded
    @POST(URLHelper.TAKE_ATTENDANCE)
    Observable<Response<JsonElement>> TakeAttendance(@Field("api_key") String api_key, @Field("course_offer_section_id") String course_offer_section_id, @Field("absence") String absence, @Field("date") String date);

    @FormUrlEncoded
    @POST(URLHelper.STUDENT_ATTENDANCE_REPORT)
    Observable<Response<AttendanceReportResponse>> getStudentReport(@Field("api_key") String api_key, @Field("course_offer_section_id") String course_offer_section_id);

    @FormUrlEncoded
    @POST(URLHelper.ALL_COURSE_LIST)
    Observable<Response<CourseListResponse>> getAllCourseList(@Field("api_key") String api_key, @Field("page") int page, @Field("key") String key);

    @FormUrlEncoded
    @POST(URLHelper.COURSE_VIEW)
    Observable<Response<CourseDetailsResponse>> getCourseView(@Field("api_key") String api_key, @Field("id") String id);

    @FormUrlEncoded
    @POST(URLHelper.SINGLE_TEACHER_ALL_COURSE_LIST)
    Observable<Response<CourseListResponse>> getSingleTeacherAllCourseList(@Field("api_key") String api_key, @Field("page") int page, @Field("instructor_id") String instructor_id);

    @FormUrlEncoded
    @POST(URLHelper.COURSE_REQUEST)
    Observable<Response<Status>> getCourseRequest(@Field("api_key") String api_key, @Field("id") String id);

    @Multipart
    @POST(URLHelper.UPDATE_USER)
        //Observable<Response<JsonElement>> getTaskAssign(@Body RequestBody file);
    Observable<Response<JsonElement>> userEditProfile(@Part MultipartBody.Part attachment_file_name, @Part("api_key") RequestBody api_key, @Part("name") RequestBody name,@Part("student_id") RequestBody student_id,@Part("mobile") RequestBody mobile,@Part("api_key") RequestBody password );
}



