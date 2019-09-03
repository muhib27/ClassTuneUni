package com.classtune.classtuneuni.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.activity.MainActivity;
import com.classtune.classtuneuni.fragment.AssignmentDetailsFragment;
import com.classtune.classtuneuni.fragment.ResourceViewFragment;
import com.classtune.classtuneuni.fragment.TeacherNoticeDetails;
import com.classtune.classtuneuni.home.StDueSubmission;
import com.classtune.classtuneuni.home.StHomeAttendance;
import com.classtune.classtuneuni.home.StHomeFeed;
import com.classtune.classtuneuni.home.StHomeHeaderData;
import com.classtune.classtuneuni.home.StNextClass;
import com.classtune.classtuneuni.model.AssignmentModel;
import com.classtune.classtuneuni.utils.AppUtility;
import com.classtune.classtuneuni.utils.PaginationAdapterCallback;
import com.classtune.classtuneuni.utils.UIHelper;
import com.classtune.classtuneuni.utils.URLHelper;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class StHomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<StHomeFeed> mValues;
    private List<StHomeAttendance> stHomeAttendanceList;
    private Context mContext;
    protected ItemListener mListener;
    private static final int HEADER = 0;
    private static final int NOTICE = 1;
    private static final int ASSIGNMENT = 5;
    private static final int ASSIGNMENT_MARK = 7;
    private static final int RESOURCE = 8;
    private static final int EXAM_SCHEDULE = 2;
    private static final int EXAM_REPORT = 3;


    private static final int LOADING = 10;

    public TextView headerSubCode;

   // public static final String BASE_URL = "http://192.168.3.48";
    public static final String BASE_URL= "http://uni.edoozz.com/";



    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;

    private PaginationAdapterCallback mCallback;

    private String errorMsg;


    public StHomeAdapter(Context context, PaginationAdapterCallback mCallback) {
        mValues = new ArrayList<>();
        stHomeAttendanceList = new ArrayList<>();
        mContext = context;
        this.mCallback = mCallback;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(mContext).inflate(R.layout.row_item, parent, false);
//        return new ViewHolder(view);
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ASSIGNMENT:
                View viewAssignment = inflater.inflate(R.layout.home_assignment_item_row, parent, false);
                viewHolder = new AssignmentView(viewAssignment);
                break;
            case ASSIGNMENT_MARK:
                View viewAssignmentMark = inflater.inflate(R.layout.home_assignment_mark_item_row, parent, false);
                viewHolder = new AssignmentMarkView(viewAssignmentMark);
                break;

            case NOTICE:
                View noticeView = inflater.inflate(R.layout.home_notice_item_row, parent, false);
                viewHolder = new NoticeView(noticeView);
                break;
            case EXAM_SCHEDULE:
                View viewExam = inflater.inflate(R.layout.home_exam_item_row, parent, false);
                viewHolder = new ExamView(viewExam);
                break;
            case EXAM_REPORT:
                View viewExamResult = inflater.inflate(R.layout.home_exam_result_item_row, parent, false);
                viewHolder = new ExamResultView(viewExamResult);
                break;
            case RESOURCE:
                View viewResource = inflater.inflate(R.layout.home_resource_item_row, parent, false);
                viewHolder = new ResourceView(viewResource);
                break;
            case LOADING:
                View viewLoading = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(viewLoading);
                break;
            case HEADER:
                View viewHeader = inflater.inflate(R.layout.home_header, parent, false);
                viewHolder = new Header(viewHeader);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        final StHomeFeed result = mValues.get(position);
        switch (getItemViewType(position)) {

            case ASSIGNMENT:
                final AssignmentView assignmentHolder = (AssignmentView) viewHolder;
                if (result.getContentName() != null)
                    assignmentHolder.subtitle.setText(result.getContentName());
                if (result.getInstructor() != null)
                    assignmentHolder.name.setText(result.getInstructor());
                if (result.getFeedTime() != null)
                    assignmentHolder.time.setText(result.getFeedTime());
                if (result.getCourseCode() != null)
                    assignmentHolder.subject.setText(result.getCourseCode());
                if (result.getDueDate() != null)
                    assignmentHolder.date.setText(AppUtility.getDateString(result.getDueDate(),AppUtility.DATE_FORMAT_APP, AppUtility.DATE_FORMAT_SERVER));
                if (result.getAssignmentMark() != null)
                    assignmentHolder.marks.setText(""+result.getAssignmentMark());
                if(result.getInstructorImage()!=null)
                Glide.with(mContext)
                        .load( BASE_URL + result.getInstructorImage())
                        //.load("http://via.placeholder.com/300.png")
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                // log exception
                                Log.e("TAG", "Error loading image", e);
                                return false; // important to return false so the error placeholder can be placed
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                return false;
                            }
                        })
                        .into(assignmentHolder.pic);

                assignmentHolder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Fragment fragment = new AssignmentDetailsFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("assignmentId", result.getAssignmentId());
                        gotoFragment(fragment, "assignmentDetailsFragment", bundle);

                    }
                });


                break;
            case ASSIGNMENT_MARK:
                final AssignmentMarkView assignmentMarkView = (AssignmentMarkView) viewHolder;
                if (result.getContentName() != null)
                    assignmentMarkView.subtitle.setText(result.getContentName());
                if (result.getInstructor() != null)
                    assignmentMarkView.name.setText(result.getInstructor());
                if (result.getFeedTime() != null)
                    assignmentMarkView.time.setText(result.getFeedTime());
                if (result.getCourseCode() != null)
                    assignmentMarkView.subject.setText(result.getCourseCode());
                if (result.getDueDate() != null)
                    assignmentMarkView.date.setText(AppUtility.getDateString(result.getDueDate(),AppUtility.DATE_FORMAT_APP, AppUtility.DATE_FORMAT_SERVER));
                if (result.getAssignmentMark() != null && result.getObtainedMark()!=null)
                    assignmentMarkView.marks.setText(""+ result.getObtainedMark() + "/" +result.getAssignmentMark() );
                if(result.getInstructorImage()!=null)
                Glide.with(mContext)
                        .load(BASE_URL + result.getInstructorImage())
                       // .load("https://champs21.com/wp-content/uploads/2019/08/dengue.jpg")
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                // log exception
                                Log.e("TAG", "Error loading image", e);
                                return false; // important to return false so the error placeholder can be placed
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                return false;
                            }
                        })
                        .into(assignmentMarkView.pic);
                assignmentMarkView.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Fragment fragment = new AssignmentDetailsFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("assignmentId", result.getAssignmentId());
                        gotoFragment(fragment, "assignmentDetailsFragment", bundle);
                    }
                });
                break;
            case NOTICE:
                final NoticeView noticeViewHolder = (NoticeView) viewHolder;
                if (result.getInstructor() != null)
                    noticeViewHolder.name.setText(result.getInstructor());
                if (result.getFeedTime() != null)
                    noticeViewHolder.time.setText(result.getFeedTime());
                if (result.getCourseCode() != null)
                    noticeViewHolder.subject.setText(result.getCourseCode());
                if(result.getInstructorImage()!=null)
                Glide.with(mContext)
                        .load(BASE_URL + result.getInstructorImage())
                        //.load("http://via.placeholder.com/300.png")
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                // log exception
                                Log.e("TAG", "Error loading image", e);
                                return false; // important to return false so the error placeholder can be placed
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                return false;
                            }
                        })
                        .into(noticeViewHolder.pic);
                noticeViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Fragment fragment = new TeacherNoticeDetails();
                        Bundle bundle = new Bundle();
                        bundle.putString("noticeId", result.getNoticeId());
                        gotoFragment(fragment, "assignmentDetailsFragment", bundle);
                    }
                });

                break;
            case HEADER:
                final Header headerHolder = (Header) viewHolder;
                //  heroHolder.title.setText(result.getContent());
                headerHolder.attendanceSubCode.setText(stHomeAttendanceList.get(0).getCourseCode());
                headerHolder.attendancePresent.setText(""+stHomeAttendanceList.get(0).getPresent() + "/"  + stHomeAttendanceList.get(0).getTotalClass());
                headerHolder.attendanceParcent.setText(""+ stHomeAttendanceList.get(0).getPercentage() + "%");

                if(stNextClass != null && stNextClass.getCourseCode()!=null)
                headerHolder.nextSubCode.setText(stNextClass.getCourseCode());
                if(stNextClass != null && stNextClass.getInstructor()!=null)
                headerHolder.nextTeacher.setText(stNextClass.getInstructor());
                if(stNextClass != null && stNextClass.getStartTime()!=null)
                headerHolder.nextTime.setText(getTime(stNextClass.getStartTime().substring(0, 5)));

                if(stDueSubmission != null && stDueSubmission.getCourseCode()!=null)
                headerHolder.dueSubCode.setText(stDueSubmission.getCourseCode());
                if(stDueSubmission != null && stDueSubmission.getCourseName()!=null)
                headerHolder.dueSubject.setText(stDueSubmission.getCourseName());
                if(stDueSubmission != null && stDueSubmission.getDueDate()!=null) {
                    headerHolder.dueDate.setText(getDate(stDueSubmission.getDueDate()));
                    headerHolder.month.setText(AppUtility.getMonth(stDueSubmission.getDueDate().substring(4,6)));
                }
                //AppUtility.getDateString(stDueSubmission.getDueDate(), AppUtility.DATE_FORMAT_D_M, AppUtility.DATE_FORMAT_SERVER)

            //    AppUtility.getMonth(routine.getMonth()) + ", " + routine.getYear()


                break;

            case EXAM_SCHEDULE:
                final ExamView examViewHolder = (ExamView) viewHolder;
                if (result.getInstructor() != null)
                    examViewHolder.name.setText(result.getInstructor());
                if (result.getFeedTime() != null)
                    examViewHolder.time.setText(result.getFeedTime());
                if (result.getCourseCode() != null)
                    examViewHolder.subject.setText(result.getCourseCode());
                if (result.getExamDate() != null)
                    examViewHolder.date.setText(AppUtility.getDateString(result.getExamDate(),AppUtility.DATE_FORMAT_APP, AppUtility.DATE_FORMAT_SERVER));
                if (result.getExamMark() != null)
                    examViewHolder.marks.setText(""+result.getExamMark());
                if(result.getInstructorImage()!=null)
                Glide.with(mContext)
                        .load(BASE_URL + result.getInstructorImage())
                        //.load("http://via.placeholder.com/300.png")
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                // log exception
                                Log.e("TAG", "Error loading image", e);
                                return false; // important to return false so the error placeholder can be placed
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                return false;
                            }
                        })
                        .into(examViewHolder.pic);

                examViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

                break;
            case EXAM_REPORT:
                final ExamResultView examResultViewHolder = (ExamResultView) viewHolder;
                if (result.getInstructor() != null)
                    examResultViewHolder.name.setText(result.getInstructor());
                if (result.getFeedTime() != null)
                    examResultViewHolder.time.setText(result.getFeedTime());
                if (result.getCourseCode() != null)
                    examResultViewHolder.subject.setText(result.getCourseCode());
                if (result.getExamDate() != null)
                    examResultViewHolder.date.setText(AppUtility.getDateString(result.getExamDate(),AppUtility.DATE_FORMAT_APP, AppUtility.DATE_FORMAT_SERVER));
                if (result.getExamMark() != null && result.getObtainedMark()!= null)
                    examResultViewHolder.marks.setText(""+ result.getObtainedMark() + "/" + result.getExamMark());
                if(result.getInstructorImage()!=null)
                Glide.with(mContext)
                        .load(BASE_URL + result.getInstructorImage())
                        //.load("http://via.placeholder.com/300.png")
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                // log exception
                                Log.e("TAG", "Error loading image", e);
                                return false; // important to return false so the error placeholder can be placed
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                return false;
                            }
                        })
                        .into(examResultViewHolder.pic);
                examResultViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

                break;
            case RESOURCE:
                final ResourceView resourceView = (ResourceView) viewHolder;
                if (result.getInstructor() != null)
                    resourceView.name.setText(result.getInstructor());
                if (result.getFeedTime() != null)
                    resourceView.time.setText(result.getFeedTime());
                if (result.getCourseCode() != null) {
                    resourceView.subject.setText(result.getCourseCode());
                    resourceView.subjectCode.setText(result.getCourseCode());
                }
                if (result.getTitle() != null)
                    resourceView.title.setText(result.getInstructor());
                if (result.getCourseName() != null)
                    resourceView.course.setText(result.getCourseName());
                if(result.getInstructorImage()!=null)
                Glide.with(mContext)
                        .load(BASE_URL + result.getInstructorImage())
                        //.load("http://via.placeholder.com/300.png")
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                // log exception
                                Log.e("TAG", "Error loading image", e);
                                return false; // important to return false so the error placeholder can be placed
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                return false;
                            }
                        })
                        .into(resourceView.pic);
                if(result.getThumbnail()!=null)
                    Glide.with(mContext)
                            .load(result.getThumbnail())
                            //.load("http://via.placeholder.com/300.png")
                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    // log exception
                                    Log.e("TAG", "Error loading image", e);
                                    return false; // important to return false so the error placeholder can be placed
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    return false;
                                }
                            })
                            .into(resourceView.resurceImg);
                resourceView.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Fragment fragment =new ResourceViewFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("title", result.getCourseName());
                        bundle.putString("subCode", result.getMaterialTitle());
                        bundle.putString("content", result.getMaterialContent());
                        gotoFragment(fragment, "resourceViewFragment", bundle);
                    }
                });

                break;
            case LOADING:
                LoadingVH loadingVH = (LoadingVH) viewHolder;

                if (retryPageLoad) {
                    loadingVH.mErrorLayout.setVisibility(View.VISIBLE);
                    loadingVH.mProgressBar.setVisibility(View.GONE);

                    loadingVH.mErrorTxt.setText(
                            errorMsg != null ?
                                    errorMsg :
                                    mContext.getString(R.string.error_msg_unknown));

                } else {
                    loadingVH.mErrorLayout.setVisibility(View.GONE);
                    loadingVH.mProgressBar.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    private String getDate(String dueDate) {
        String[] parts = dueDate.split("-");


        return parts[2];
    }

    private String getTime(String st){
        String time = "";
        if(st.length()>2) {

            if (Integer.parseInt(st.substring(0, 2)) >= 12) {
                time = st + "pm";
            } else {
                time = st + "am";
            }

        }
        return time;
    }

//    @Override
//    public void onBindViewHolder(ViewHolder viewHolder, int position) {
//        viewHolder.setData(mValues.get(position));
//    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @Override
    public int getItemViewType(int position) {

        if (position == mValues.size() - 1 && isLoadingAdded)
            return LOADING;
        else {
            if (mValues.get(position).getContentType()!=null && mValues.get(position).getContentType() == 100)
                return HEADER;
            if (mValues.get(position).getContentType()!=null && mValues.get(position).getContentType() == 1)
                return NOTICE;
            else if (mValues.get(position).getContentType()!=null && mValues.get(position).getContentType() == 2)
                return EXAM_SCHEDULE;
            else if (mValues.get(position).getContentType()!=null && mValues.get(position).getContentType() == 3)
                return EXAM_REPORT;
            else if (mValues.get(position).getContentType()!=null && mValues.get(position).getContentType() == 5)
                return ASSIGNMENT;
            else if (mValues.get(position).getContentType()!=null && mValues.get(position).getContentType() == 7)
                return ASSIGNMENT_MARK;
            else
                //if (mValues.get(position).getContentType() == 8)
                return RESOURCE;

        }
    }

    public interface ItemListener {
        void onItemClick(AssignmentModel item, int pos);
    }


    protected class AssignmentView extends RecyclerView.ViewHolder {
        private TextView subtitle;
        private TextView name, marks;
        private TextView date; // displays "year | language"
        private ImageView pic;
        private ProgressBar mProgress;
        private TextView subject, time, present, total;
        private FrameLayout itemLayout;
        CardView cardView;
        // CircleImageView pic;
        LinearLayout numLl, imgLl;

        public AssignmentView(View itemView) {
            super(itemView);
//            assignedDate = itemView.findViewById(R.id.assignedDate);
            date = itemView.findViewById(R.id.date);
            pic = itemView.findViewById(R.id.pic);
            name = itemView.findViewById(R.id.name);
            subtitle = itemView.findViewById(R.id.subtitle);
            subject = itemView.findViewById(R.id.subject);
            time = itemView.findViewById(R.id.time);
            marks = itemView.findViewById(R.id.marks);
            cardView = itemView.findViewById(R.id.cardView);
//            present = itemView.findViewById(R.id.present);
//            total = itemView.findViewById(R.id.total);
//            img = itemView.findViewById(R.id.img);
////            assignedDate = itemView.findViewById(R.id.assignedDate);
//            cardView = itemView.findViewById(R.id.cardView);
//            imgLl = itemView.findViewById(R.id.imgLl);
//            numLl = itemView.findViewById(R.id.numLl);

        }
    }

    protected class AssignmentMarkView extends RecyclerView.ViewHolder {
        private TextView subtitle, marks;
        private TextView name;
        private TextView date; // displays "year | language"
        private ImageView pic;
        // private ProgressBar pic;
        private TextView subject, time, present, total;
        private FrameLayout itemLayout;
        CardView cardView;
        // CircleImageView pic;
        LinearLayout numLl, imgLl;

        public AssignmentMarkView(View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.date);
            pic = itemView.findViewById(R.id.pic);
            name = itemView.findViewById(R.id.name);
            subtitle = itemView.findViewById(R.id.subtitle);
            subject = itemView.findViewById(R.id.subject);
            time = itemView.findViewById(R.id.time);
            marks = itemView.findViewById(R.id.marks);
            cardView = itemView.findViewById(R.id.cardView);
//            section = itemView.findViewById(R.id.section);
//            present = itemView.findViewById(R.id.present);
//            total = itemView.findViewById(R.id.total);
//            img = itemView.findViewById(R.id.img);
////            assignedDate = itemView.findViewById(R.id.assignedDate);
//            cardView = itemView.findViewById(R.id.cardView);
//            imgLl = itemView.findViewById(R.id.imgLl);
//            numLl = itemView.findViewById(R.id.numLl);

        }
    }

    protected class NoticeView extends RecyclerView.ViewHolder {
        private TextView subtitle, name, subject, time;
        private TextView mMovieDesc;
        private TextView mYear; // displays "year | language"
        private ImageView pic;
        private CardView cardView;
        // CircleImageView pic;

        public NoticeView(View itemView) {
            super(itemView);


            pic = itemView.findViewById(R.id.pic);
            name = itemView.findViewById(R.id.name);
            subtitle = itemView.findViewById(R.id.subtitle);
            subject = itemView.findViewById(R.id.subject);
            time = itemView.findViewById(R.id.time);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }

    protected class Header extends RecyclerView.ViewHolder {
        private TextView mMovieDesc;
        private TextView month; // displays "year | language"
        private ImageView mPosterImg;
        private CardView cardView;
        CircleImageView pic;
        public TextView title, attendanceSubCode, attendancePresent, attendanceParcent, nextSubCode, nextTeacher, nextTime, dueSubCode, dueSubject, dueDate;


        public Header(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text);
            attendanceSubCode = itemView.findViewById(R.id.headerSubCode);
            attendancePresent = itemView.findViewById(R.id.total);
            attendanceParcent = itemView.findViewById(R.id.parcentage);

            nextSubCode = itemView.findViewById(R.id.nextSubCode);
            nextTeacher = itemView.findViewById(R.id.nextTeacher);
            nextTime = itemView.findViewById(R.id.nextClassTime);

            dueSubCode = itemView.findViewById(R.id.dueSubCode);
            dueSubject = itemView.findViewById(R.id.dueTitle);
            dueDate = itemView.findViewById(R.id.date);
            month = itemView.findViewById(R.id.month);
           // pic = itemView.findViewById(R.id.pic);
        }
    }

    protected class ExamView extends RecyclerView.ViewHolder {
        private TextView subtitle, name, subject, time, marks;
        private TextView mMovieDesc;
        private TextView mYear, date; // displays "year | language"
        private ImageView pic;
        private CardView cardView;
        //CircleImageView pic;

        public ExamView(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            pic = itemView.findViewById(R.id.pic);
            name = itemView.findViewById(R.id.name);
            subtitle = itemView.findViewById(R.id.subtitle);
            subject = itemView.findViewById(R.id.subject);
            time = itemView.findViewById(R.id.time);
            marks = itemView.findViewById(R.id.marks);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }

    protected class ExamResultView extends RecyclerView.ViewHolder {
        private TextView subtitle, date, name, subject, time, marks;
        private TextView mMovieDesc;
        private TextView mYear; // displays "year | language"
        private ImageView pic;
        private CardView cardView;
        //CircleImageView pic;

        public ExamResultView(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            pic = itemView.findViewById(R.id.pic);
            name = itemView.findViewById(R.id.name);
            subtitle = itemView.findViewById(R.id.subtitle);
            subject = itemView.findViewById(R.id.subject);
            time = itemView.findViewById(R.id.time);
            marks = itemView.findViewById(R.id.marks);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }

    protected class ResourceView extends RecyclerView.ViewHolder {
        private TextView title, time, name, date, subject, course, subjectCode;
        private TextView mMovieDesc;
        private TextView mYear; // displays "year | language"
        private ImageView pic, resurceImg;
        private CardView cardView;
        // CircleImageView pic;

        public ResourceView(View itemView) {
            super(itemView);
            pic = itemView.findViewById(R.id.pic);
            resurceImg = itemView.findViewById(R.id.resourseImg);
            name = itemView.findViewById(R.id.name);
            title = itemView.findViewById(R.id.title);
            subject = itemView.findViewById(R.id.subject);
            subjectCode = itemView.findViewById(R.id.subjectCode);
            course = itemView.findViewById(R.id.course);
            time = itemView.findViewById(R.id.time);
            cardView = itemView.findViewById(R.id.cardView);


        }
    }

    protected class LoadingVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ProgressBar mProgressBar;
        private ImageButton mRetryBtn;
        private TextView mErrorTxt;
        private LinearLayout mErrorLayout;

        public LoadingVH(View itemView) {
            super(itemView);

            mProgressBar = (ProgressBar) itemView.findViewById(R.id.loadmore_progress);
            mRetryBtn = (ImageButton) itemView.findViewById(R.id.loadmore_retry);
            mErrorTxt = (TextView) itemView.findViewById(R.id.loadmore_errortxt);
            mErrorLayout = (LinearLayout) itemView.findViewById(R.id.loadmore_errorlayout);

            mRetryBtn.setOnClickListener(this);
            mErrorLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.loadmore_retry:
                case R.id.loadmore_errorlayout:

                    showRetry(false, null);
                    mCallback.retryPageLoad();

                    break;
            }
        }
    }

    public void showRetry(boolean show, @Nullable String errorMsg) {
        retryPageLoad = show;
        notifyItemChanged(mValues.size() - 1);

        if (errorMsg != null) this.errorMsg = errorMsg;
    }

    public void add(StHomeFeed r) {
        mValues.add(r);
        notifyItemInserted(mValues.size() - 1);
    }

    public void addAllData(List<StHomeFeed> moveResults) {
        for (StHomeFeed result : moveResults) {
            add(result);
        }
    }

//    public void addHeader(StHomeFeed r) {
//        mValues.add(r);
//        notifyItemInserted(mValues.size() - 1);
//    }

    StNextClass stNextClass;
    StDueSubmission stDueSubmission;
    public void addAllHeader(StHomeHeaderData stHomeHeaderData) {
//        for (StHomeAttendance result : moveResults) {
//            add(result);
//        }

        stHomeAttendanceList = stHomeHeaderData.getAttendance();
        stNextClass = stHomeHeaderData.getNextClass();
        stDueSubmission = stHomeHeaderData.getDueSubmission();
    }

    public void remove(StHomeFeed r) {
        int position = mValues.indexOf(r);
        if (position > -1) {
            mValues.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new StHomeFeed());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = mValues.size() - 1;
        StHomeFeed result = getItem(position);

        if (result != null) {
            mValues.remove(position);
            notifyItemRemoved(position);
        }
    }

    public StHomeFeed getItem(int position) {
        return mValues.get(position);
    }

    private void gotoFragment(Fragment fragment, String tag, Bundle bundle) {
        // load fragment

        fragment.setArguments(bundle);
        FragmentTransaction transaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContainer, fragment, tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}