package com.example.solidcourse.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.solidcourse.dataClasses.course.Course;
import com.example.solidcourse.dataClasses.course.Lesson;
import com.example.solidcourse.dataClasses.course.Paragraph;
import com.example.solidcourse.dataClasses.course.Task;
import com.example.solidcourse.dataClasses.course.tasks.CountTask;
import com.example.solidcourse.dataClasses.course.tasks.StudyTask;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class MyCoursesDataBase {
    private static final String DATA_BASE_NAME = "created_courses.db";
    private static final int DATA_BASE_VERSION = 10;
    CourseTable courseTable;
    ParagraphTable paragraphTable;
    LessonTable lessonTable;
    StudyTaskTable studyTaskTable;
    CountTaskTable countTaskTable;
// OpenHelper!
    private class OpenHelper extends SQLiteOpenHelper {
        public OpenHelper(Context context) {
            super(context, DATA_BASE_NAME, null, DATA_BASE_VERSION);
        }
        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
            CourseTable.getInstance(sqLiteDatabase).dropTable();
            ParagraphTable.getInstance(sqLiteDatabase).dropTable();
            LessonTable.getInstance(sqLiteDatabase).dropTable();
            StudyTaskTable.getInstance(sqLiteDatabase).dropTable();
            CountTaskTable.getInstance(sqLiteDatabase).dropTable();
            onCreate(sqLiteDatabase);
        }
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            CourseTable.getInstance(sqLiteDatabase).createTable();
            ParagraphTable.getInstance(sqLiteDatabase).createTable();
            LessonTable.getInstance(sqLiteDatabase).createTable();
            StudyTaskTable.getInstance(sqLiteDatabase).createTable();
            CountTaskTable.getInstance(sqLiteDatabase).createTable();
        }
    }

    public MyCoursesDataBase(Context context) {
        OpenHelper openHelper = new OpenHelper(context);
        SQLiteDatabase database = openHelper.getWritableDatabase();
        courseTable = CourseTable.getInstance(database);
        paragraphTable = ParagraphTable.getInstance(database);
        lessonTable = LessonTable.getInstance(database);
        studyTaskTable = StudyTaskTable.getInstance(database);
        countTaskTable = CountTaskTable.getInstance(database);
    }

    public boolean existsCourse(long id) {
        return courseTable.exists(id);
    }

    public Course selectCourse(long id) {
        Course course = courseTable.select(id);
        course.setParagraphs(selectAllParagraphs(id));
        return course;
    }

    public List<Course> selectAllCourses() {
        List<Course> courses = courseTable.selectAll();
        for (Course course : courses) {
            course.setParagraphs(selectAllParagraphs(course.getId()));
        }
        return courses;
    }

    public int updateCourse(Course course) {
        int result = courseTable.update(course);
        deleteAllParagraph(course.getId());
        for (Paragraph paragraph : course.getParagraphs()) {
            paragraph.setCourseId(course.getId());
            insertParagraph(paragraph);
        }
        return result;
    }

    public void deleteCourse(long courseId) {
        courseTable.delete(courseId);
    }

    public void deleteAllCourses() {
        courseTable.deleteAll();
    }

    public long insertCourse(Course course) {

        long id = courseTable.insert(course);
        for (Paragraph paragraph : course.getParagraphs()) {
            paragraph.setCourseId(course.getId());
            insertParagraph(paragraph);
        }
        return id;
    }

    public Paragraph selectParagraph(long id) {
        Paragraph paragraph = paragraphTable.select(id);
        paragraph.setLessons(selectAllLessons(id));
        return paragraph;
    }

    public List<Paragraph> selectAllParagraphs(long courseId) {
        List<Paragraph> paragraphs = paragraphTable.selectAll(courseId);
        for (Paragraph paragraph : paragraphs) {
            paragraph.setLessons(selectAllLessons(paragraph.getId()));
        }
        return paragraphs;
    }

    public long insertParagraph(Paragraph paragraph) {
        long id = paragraphTable.insert(paragraph);
        for (Lesson lesson : paragraph.getLessons()) {
            lesson.setParagraphId(paragraph.getId());
            insertLesson(lesson);
        }
        return id;
    }

    public void deleteParagraph(long id) {
        paragraphTable.delete(id);
    }

    public void deleteAllParagraph(long courseId) {
        paragraphTable.deleteAll(courseId);
    }


    public int updateParagraph(Paragraph paragraph) {
        int result = paragraphTable.update(paragraph);
        deleteAllLessons(paragraph.getId());
        for (Lesson lesson : paragraph.getLessons()) {
            lesson.setParagraphId(paragraph.getId());
            lessonTable.insert(lesson);
        }
        return result;
    }


    //
    public Lesson selectLesson(long id) {
        Lesson lesson = lessonTable.select(id);
        List<Task> tasks = new ArrayList<>();
        tasks.addAll(selectAllStudyTasks(lesson.getId()));
        tasks.addAll(selectAllCountTasks(lesson.getId()));
        lesson.setTasks(tasks);
        return lesson;
    }

    public List<Lesson> selectAllLessons(long paragraphId) {
        List<Lesson> lessons = lessonTable.selectAll(paragraphId);
        for (Lesson lesson : lessons) {
            List<Task> tasks= new ArrayList<>();
            tasks.addAll(selectAllStudyTasks(lesson.getId()));
            tasks.addAll(selectAllCountTasks(lesson.getId()));
            lesson.setTasks(tasks);
        }
        return lessons;
    }

    public long insertLesson(Lesson lesson) {
        long id = lessonTable.insert(lesson);
        for (Task task : lesson.getTasks()) {
            task.setLessonId(id);
            if (task instanceof StudyTask) {
                studyTaskTable.insert((StudyTask) task);
            } else if (task instanceof CountTask) {
                countTaskTable.insert((CountTask) task);
            }
        }
        return id;
    }

    public void deleteLesson(long id) {
        lessonTable.delete(id);
    }

    public void deleteAllLessons(long paragraphId) {
        lessonTable.deleteAll(paragraphId);
    }

    public int updateLesson(Lesson lesson) {
        int result = lessonTable.update(lesson);
        deleteAllCountTask(lesson.getId());
        deleteAllStudyTask(lesson.getId());
        for (Task task : lesson.getTasks()) {
            task.setLessonId(lesson.getId());
            if (task instanceof StudyTask) {
                insertStudyTask((StudyTask) task);
            } else if (task instanceof CountTask) {
                insertCountTask((CountTask) task);
            }
        }
        return result;
    }

    //
    public StudyTask selectStudyTask(long id) {
        return studyTaskTable.select(id);
    }

    public List<StudyTask> selectAllStudyTasks(long lessonId) {
        return studyTaskTable.selectAll(lessonId);
    }

    public long insertStudyTask(StudyTask studyTask) {
        return studyTaskTable.insert(studyTask);
    }

    public void deleteStudyTask(long id) {
        studyTaskTable.delete(id);
    }
    public void deleteAllStudyTask(long lessonId) {
        studyTaskTable.deleteAll(lessonId);
    }
    public int updateStudyTask(StudyTask studyTask) {
        return studyTaskTable.update(studyTask);
    }

    public CountTask selectCountTask(long id) {
        return countTaskTable.select(id);
    }

    public List<CountTask> selectAllCountTasks(long lessonId) {
        return countTaskTable.selectAll(lessonId);
    }

    public long insertCountTask(CountTask countTask) {
        return countTaskTable.insert(countTask);
    }

    public void deleteCountTask(long id) {
        countTaskTable.delete(id);
    }
    public void deleteAllCountTask(long lessonId) {
        countTaskTable.deleteAll(lessonId);
    }
    public int updateCountTask(CountTask countTask) {
        return countTaskTable.update(countTask);
    }

    private static class CourseTable {
        SQLiteDatabase database;
        // SINGLETON!
        private static volatile CourseTable instance;

        private CourseTable(SQLiteDatabase database) {
            this.database = database;
        }

        public static CourseTable getInstance(SQLiteDatabase database) {
            if (instance == null) {
                synchronized (CourseTable.class) {
                    if (instance == null) {
                        instance = new CourseTable(database);
                    }
                }
            }
            return instance;
        }

        // CREATE AND DROP TABLE
        public void createTable() {
            String courseQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " NTEXT, " +
                    COLUMN_AUTHOR + " NTEXT);";
            database.execSQL(courseQuery);
        }

        public void dropTable() {
            database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        }

        // TABLE EDITING METHODS
        public boolean exists(long id) {
            String query = "SELECT * FROM " + TABLE_NAME +
                    " WHERE " + COLUMN_ID + " = " + id;
            Cursor cursor = database.rawQuery(query, null);
            int count = cursor.getCount();
            cursor.close();
            return count > 0;
        }

        public long insert(Course course) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_NAME, course.getName());
            contentValues.put(COLUMN_AUTHOR, course.getAuthor());
            contentValues.put(COLUMN_ID, course.getId());
            database.insert(TABLE_NAME, null, contentValues);
            return course.getId();
        }

        public Course select(long id) {
            Cursor cursor = database.query(TABLE_NAME, null,
                    COLUMN_ID + " = ?",
                    new String[]{String.valueOf(id)},
                    null, null, null);

            cursor.moveToFirst();
            String name = cursor.getString(NUM_COLUMN_NAME);
            String author = cursor.getString(NUM_COLUMN_AUTHOR);
            Course course = new Course(name, author);
            course.setId(id);
            cursor.close();
            return course;
        }

        public List<Course> selectAll() {
            List<Course> courses = new ArrayList<>();
            Cursor cursor = database.query(TABLE_NAME,
                    null, null, null,
                    null, null, null);

            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                do {
                    long id = cursor.getLong(NUM_COLUMN_ID);
                    String name = cursor.getString(NUM_COLUMN_NAME);
                    String author = cursor.getString(NUM_COLUMN_AUTHOR);
                    Course course = new Course(name, author);
                    course.setId(id);
                    courses.add(course);
                } while (cursor.moveToNext());
            }

            cursor.close();
            return courses;
        }

        public int update(Course course) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_ID, course.getId());
            contentValues.put(COLUMN_NAME, course.getName());
            contentValues.put(COLUMN_AUTHOR, course.getAuthor());
            return database.update(TABLE_NAME, contentValues,
                    COLUMN_ID + " = ?", // изменяем где id == нашему
                    new String[]{ String.valueOf(course.getId()) });
        }

        public void delete(long id) {
            database.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{ String.valueOf(id) });
        }

        public void deleteAll() {
            database.delete(TABLE_NAME, null, null);
        }
        // TABLE FIELDS
        private static final String TABLE_NAME = "courses";

        private static final String COLUMN_ID = "id";
        private static final String COLUMN_NAME = "name";
        private static final String COLUMN_AUTHOR = "author";
        private static final int NUM_COLUMN_ID = 0;
        private static final int NUM_COLUMN_NAME = 1;
        private static final int NUM_COLUMN_AUTHOR = 2;
    }
    private static class ParagraphTable {
        SQLiteDatabase database;
        // SINGLETON!
        private static volatile ParagraphTable instance;

        private ParagraphTable(SQLiteDatabase database) {
            this.database = database;
        }

        public static ParagraphTable getInstance(SQLiteDatabase database) {
            if (instance == null) {
                synchronized (ParagraphTable.class) {
                    if (instance == null) {
                        instance = new ParagraphTable(database);
                    }
                }
            }
            return instance;
        }
        // CREATE AND DROP TABLE
        public void createTable() {
            String courseQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_COURSE_ID + " INTEGER, " +
                    COLUMN_NAME + " NTEXT, " +
                    "FOREIGN KEY (" + COLUMN_COURSE_ID + ") " +
                    "REFERENCES " + CourseTable.TABLE_NAME + " (" + CourseTable.COLUMN_ID + ") " +
                    "ON UPDATE CASCADE " +
                    "ON DELETE CASCADE " +
                    "); ";
            database.execSQL(courseQuery);
        }

        public void dropTable() {
            database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        }

        // TABLE EDITING METHODS
        public long insert(Paragraph paragraph) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_COURSE_ID, paragraph.getCourseId());
            contentValues.put(COLUMN_NAME, paragraph.getName());
            long id = database.insert(TABLE_NAME, null, contentValues);
            paragraph.setId(id);
            return id;
        }

        public Paragraph select(long id) {
            Cursor cursor = database.query(TABLE_NAME, null,
                    COLUMN_ID + " = ?",
                    new String[]{String.valueOf(id)},
                    null, null, null);
            cursor.moveToFirst();
            String name = cursor.getString(NUM_COLUMN_NAME);
            long courseId = cursor.getLong(NUM_COLUMN_COURSE_ID);
            Paragraph paragraph = new Paragraph(name);
            paragraph.setId(id);
            paragraph.setCourseId(courseId);
            cursor.close();
            return paragraph;
        }

        public List<Paragraph> selectAll(long courseId) {
            List<Paragraph> paragraphs = new ArrayList<>();
            Cursor cursor = database.query(TABLE_NAME, null,
                    COLUMN_COURSE_ID + " = ?",
                    new String[]{ String.valueOf(courseId) },
                    null, null, null);

            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                do {
                    long id = cursor.getLong(NUM_COLUMN_ID);
                    String name = cursor.getString(NUM_COLUMN_NAME);
                    Paragraph paragraph = new Paragraph(name);
                    paragraph.setId(id);
                    paragraph.setCourseId(courseId);
                    paragraphs.add(paragraph);
                } while (cursor.moveToNext());
            }

            cursor.close();
            return paragraphs;
        }

        public int update(Paragraph paragraph) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_ID, paragraph.getId());
            contentValues.put(COLUMN_COURSE_ID, paragraph.getCourseId());
            contentValues.put(COLUMN_NAME, paragraph.getName());
            return database.update(TABLE_NAME, contentValues,
                    COLUMN_ID + " = ?", // изменяем где id == нашему
                    new String[]{ String.valueOf( paragraph.getId()) });
        }

        public void delete(long id) {
            database.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{ String.valueOf(id) });
        }

        public void deleteAll(long courseId) {
            database.delete(TABLE_NAME, COLUMN_COURSE_ID + " = ?", new String[]{ String.valueOf(courseId) });
        }
        // TABLE FIELDS!
        private static final String TABLE_NAME = "paragraphs";

        private static final String COLUMN_ID = "id";
        private static final String COLUMN_COURSE_ID = "course_id";
        private static final String COLUMN_NAME = "name";
        private static final int NUM_COLUMN_ID = 0;
        private static final int NUM_COLUMN_COURSE_ID = 1;
        private static final int NUM_COLUMN_NAME = 2;
    }
    private static class LessonTable {
        SQLiteDatabase database;
        // SINGLETON!
        private static volatile LessonTable instance;

        private LessonTable(SQLiteDatabase database) {
            this.database = database;
        }

        public static LessonTable getInstance(SQLiteDatabase database) {
            if (instance == null) {
                synchronized (LessonTable.class) {
                    if (instance == null) {
                        instance = new LessonTable(database);
                    }
                }
            }
            return instance;
        }
        // CREATE AND DROP
        public void createTable() {
            String courseQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_PARAGRAPH_ID + " INTEGER, " +
                    COLUMN_NAME + " NTEXT, " +
                    "FOREIGN KEY (" + COLUMN_PARAGRAPH_ID + ") " +
                    "REFERENCES " + ParagraphTable.TABLE_NAME + " (" + ParagraphTable.COLUMN_ID + ") " +
                    "ON UPDATE CASCADE " +
                    "ON DELETE CASCADE " +
                    "); ";
            database.execSQL(courseQuery);
        }

        public void dropTable() {
            database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        }

        // TABLE EDITING METHOD
        public long insert(Lesson lesson) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_PARAGRAPH_ID, lesson.getParagraphId());
            contentValues.put(COLUMN_NAME, lesson.getName());
            long id = database.insert(TABLE_NAME, null, contentValues);
            lesson.setId(id);
            return id;
        }

        public Lesson select(long id) {
            Cursor cursor = database.query(TABLE_NAME, null,
                    COLUMN_ID + " = ?",
                    new String[]{String.valueOf(id)},
                    null, null, null);
            cursor.moveToFirst();
            String name = cursor.getString(NUM_COLUMN_NAME);
            long paragraphId = cursor.getLong(NUM_COLUMN_PARAGRAPH_ID);
            Lesson lesson = new Lesson(name);
            lesson.setId(id);
            lesson.setParagraphId(paragraphId);
            cursor.close();
            return lesson;
        }

        public List<Lesson> selectAll(long paragraphId) {
            List<Lesson> lessons = new ArrayList<>();
            Cursor cursor = database.query(TABLE_NAME, null,
                    COLUMN_PARAGRAPH_ID + " = ?",
                    new String[]{ String.valueOf(paragraphId) },
                    null, null, null);

            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                do {
                    long id = cursor.getLong(NUM_COLUMN_ID);
                    String name = cursor.getString(NUM_COLUMN_NAME);
                    Lesson lesson = new Lesson(name);
                    lesson.setId(id);
                    lesson.setParagraphId(paragraphId);
                    lessons.add(lesson);
                } while (cursor.moveToNext());
            }

            cursor.close();
            return lessons;
        }

        public int update(Lesson lesson) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_ID, lesson.getId());
            contentValues.put(COLUMN_PARAGRAPH_ID, lesson.getParagraphId());
            contentValues.put(COLUMN_NAME, lesson.getName());
            return database.update(TABLE_NAME, contentValues,
                    COLUMN_ID + " = ?", // изменяем где id == нашему
                    new String[]{ String.valueOf( lesson.getId()) });
        }

        public void delete(long id) {
            database.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{ String.valueOf(id) });
        }

        public void deleteAll(long paragraphId) {
            database.delete(TABLE_NAME, COLUMN_PARAGRAPH_ID + " = ?", new String[]{ String.valueOf(paragraphId) });
        }
        // TABLE FIELDS!
        private static final String TABLE_NAME = "lessons";

        private static final String COLUMN_ID = "id";
        private static final String COLUMN_PARAGRAPH_ID = "paragraph_id";
        private static final String COLUMN_NAME = "name";
        private static final int NUM_COLUMN_ID = 0;
        private static final int NUM_COLUMN_PARAGRAPH_ID = 1;
        private static final int NUM_COLUMN_NAME = 2;
    }
    private static class StudyTaskTable {
        SQLiteDatabase database;

        // SINGLETON!
        private static volatile StudyTaskTable instance;

        private StudyTaskTable(SQLiteDatabase database) {
            this.database = database;
        }

        public static StudyTaskTable getInstance(SQLiteDatabase database) {
            if (instance == null) {
                synchronized (StudyTaskTable.class) {
                    if (instance == null) {
                        instance = new StudyTaskTable(database);
                    }
                }
            }
            return instance;
        }
        // CREATE AND DROP TABLE METHODS
        public void createTable() {
            String courseQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_LESSON_ID + " INTEGER, " +
                    COLUMN_TEXT + " NTEXT, " +
                    COLUMN_MAX_SCORE + " INTEGER, " +
                    COLUMN_SCORE + " INTEGER, " +
                    COLUMN_STATE + " INTEGER, " +
                    "FOREIGN KEY (" + COLUMN_LESSON_ID + ") " +
                    "REFERENCES " + LessonTable.TABLE_NAME + " (" + LessonTable.COLUMN_ID + ") " +
                    "ON UPDATE CASCADE " +
                    "ON DELETE CASCADE " +
                    "); ";
            database.execSQL(courseQuery);
        }

        public void dropTable() {
            database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        }

        // TABLE EDITING METHODS
        public long insert(StudyTask studyTask) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_LESSON_ID, studyTask.getLessonId());
            contentValues.put(COLUMN_TEXT, studyTask.getText());
            contentValues.put(COLUMN_MAX_SCORE, studyTask.getMaxScore());
            contentValues.put(COLUMN_SCORE, studyTask.getScore());
            contentValues.put(COLUMN_STATE, studyTask.getState());

            long id = database.insert(TABLE_NAME, null, contentValues);
            studyTask.setId(id);
            return id;
        }

        public StudyTask select(long id) {
            Cursor cursor = database.query(TABLE_NAME, null,
                    COLUMN_ID + " = ?",
                    new String[]{String.valueOf(id)},
                    null, null, null);
            cursor.moveToFirst();
            String text = cursor.getString(NUM_COLUMN_TEXT);
            long lessonId = cursor.getLong(NUM_COLUMN_LESSON_ID);
            int maxScore = cursor.getInt(NUM_COLUMN_MAX_SCORE);
            int score = cursor.getInt(NUM_COLUMN_SCORE);
            int state = cursor.getInt(NUM_COLUMN_STATE);
            StudyTask task = new StudyTask(text, maxScore);
            task.setId(id);
            task.setLessonId(lessonId);
            task.setScore(score);
            task.setState(state);
            cursor.close();
            return task;
        }

        public List<StudyTask> selectAll(long lessonId) {
            List<StudyTask> tasks = new ArrayList<>();
            Cursor cursor = database.query(TABLE_NAME, null,
                    COLUMN_LESSON_ID + " = ?",
                    new String[]{ String.valueOf(lessonId) },
                    null, null, null);

            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                do {
                    long id = cursor.getLong(NUM_COLUMN_ID);
                    int score = cursor.getInt(NUM_COLUMN_SCORE);
                    int maxScore = cursor.getInt(NUM_COLUMN_MAX_SCORE);
                    int state = cursor.getInt(NUM_COLUMN_STATE);

                    String text = cursor.getString(NUM_COLUMN_TEXT);

                    StudyTask studyTask = new StudyTask(text, maxScore);
                    studyTask.setScore(score);
                    studyTask.setState(state);
                    studyTask.setId(id);
                    studyTask.setLessonId(lessonId);
                    tasks.add(studyTask);
                } while (cursor.moveToNext());
            }

            cursor.close();
            return tasks;
        }

        public int update(StudyTask task) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_ID, task.getId());
            contentValues.put(COLUMN_LESSON_ID, task.getLessonId());
            contentValues.put(COLUMN_TEXT, task.getText());
            contentValues.put(COLUMN_STATE, task.getState());
            contentValues.put(COLUMN_SCORE, task.getScore());
            contentValues.put(COLUMN_MAX_SCORE, task.getMaxScore());
            return database.update(TABLE_NAME, contentValues,
                    COLUMN_ID + " = ?", // изменяем где id == нашему
                    new String[]{ String.valueOf( task.getId()) });
        }

        public void delete(long id) {
            database.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{ String.valueOf(id) });
        }

        public void deleteAll(long lessonId) {
            database.delete(TABLE_NAME, COLUMN_LESSON_ID + " = ?", new String[]{ String.valueOf(lessonId) });
        }
        // TABLE FIELDS!
        private static final String TABLE_NAME = "study_tasks";

        private static final String COLUMN_ID = "id";
        private static final String COLUMN_LESSON_ID = "lesson_id";
        private static final String COLUMN_TEXT = "text";
        private static final String COLUMN_MAX_SCORE = "max_score";
        private static final String COLUMN_SCORE = "score";
        private static final String COLUMN_STATE = "state";

        private static final int NUM_COLUMN_ID = 0;
        private static final int NUM_COLUMN_LESSON_ID = 1;
        private static final int NUM_COLUMN_TEXT = 2;
        private static final int NUM_COLUMN_MAX_SCORE = 3;
        private static final int NUM_COLUMN_SCORE = 4;
        private static final int NUM_COLUMN_STATE = 5;
    }
    private static class CountTaskTable {
        SQLiteDatabase database;

        // SINGLETON!
        private static volatile CountTaskTable instance;

        private CountTaskTable(SQLiteDatabase database) {
            this.database = database;
        }

        public static CountTaskTable getInstance(SQLiteDatabase database) {
            if (instance == null) {
                synchronized (CountTaskTable.class) {
                    if (instance == null) {
                        instance = new CountTaskTable(database);
                    }
                }
            }
            return instance;
        }

        // CREATE AND DROP METHODS
        public void createTable() {
            String courseQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_LESSON_ID + " INTEGER, " +
                    COLUMN_TEXT + " NTEXT, " +
                    COLUMN_MAX_SCORE + " INTEGER, " +
                    COLUMN_SCORE + " INTEGER, " +
                    COLUMN_STATE + " INTEGER, " +
                    COLUMN_ANSWER_VALUE + " NTEXT, " +
                    "FOREIGN KEY (" + COLUMN_LESSON_ID + ") " +
                    "REFERENCES " + LessonTable.TABLE_NAME + " (" + LessonTable.COLUMN_ID + ") " +
                    "ON UPDATE CASCADE " +
                    "ON DELETE CASCADE " +
                    "); ";
            database.execSQL(courseQuery);
        }

        public void dropTable() {
            database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        }

        // TABLE EDITING METHODS
        public long insert(CountTask countTask) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_LESSON_ID, countTask.getLessonId());
            contentValues.put(COLUMN_TEXT, countTask.getText());
            contentValues.put(COLUMN_MAX_SCORE, countTask.getMaxScore());
            contentValues.put(COLUMN_SCORE, countTask.getScore());
            contentValues.put(COLUMN_STATE, countTask.getState());
            contentValues.put(COLUMN_ANSWER_VALUE, countTask.getAnswerValue());
            long id = database.insert(TABLE_NAME, null, contentValues);
            countTask.setId(id);
            return id;
        }

        public CountTask select(long id) {
            Cursor cursor = database.query(TABLE_NAME, null,
                    COLUMN_ID + " = ?",
                    new String[]{String.valueOf(id)},
                    null, null, null);
            cursor.moveToFirst();
            String text = cursor.getString(NUM_COLUMN_TEXT);
            String answerValue = cursor.getString(NUM_COLUMN_ANSWER_VALUE);
            long lessonId = cursor.getLong(NUM_COLUMN_LESSON_ID);
            int maxScore = cursor.getInt(NUM_COLUMN_MAX_SCORE);
            int score = cursor.getInt(NUM_COLUMN_SCORE);
            int state = cursor.getInt(NUM_COLUMN_STATE);
            CountTask task = new CountTask(text, maxScore, answerValue);
            task.setId(id);
            task.setLessonId(lessonId);
            task.setScore(score);
            task.setState(state);
            cursor.close();
            return task;
        }
        public List<CountTask> selectAll(long lessonId) {
            List<CountTask> tasks = new ArrayList<>();
            Cursor cursor = database.query(TABLE_NAME, null,
                    COLUMN_LESSON_ID + " = ?",
                    new String[]{ String.valueOf(lessonId) },
                    null, null, null);

            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                do {
                    long id = cursor.getLong(NUM_COLUMN_ID);
                    int score = cursor.getInt(NUM_COLUMN_SCORE);
                    int maxScore = cursor.getInt(NUM_COLUMN_MAX_SCORE);
                    int state = cursor.getInt(NUM_COLUMN_STATE);

                    String text = cursor.getString(NUM_COLUMN_TEXT);
                    String answerValue = cursor.getString(NUM_COLUMN_ANSWER_VALUE);

                    CountTask countTask = new CountTask(text, maxScore, answerValue);
                    countTask.setScore(score);
                    countTask.setState(state);
                    countTask.setId(id);
                    countTask.setLessonId(lessonId);
                    tasks.add(countTask);
                } while (cursor.moveToNext());
            }

            cursor.close();
            return tasks;
        }

        public int update(CountTask task) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_ID, task.getId());
            contentValues.put(COLUMN_LESSON_ID, task.getLessonId());
            contentValues.put(COLUMN_TEXT, task.getText());
            contentValues.put(COLUMN_STATE, task.getState());
            contentValues.put(COLUMN_SCORE, task.getScore());
            contentValues.put(COLUMN_MAX_SCORE, task.getMaxScore());

            contentValues.put(COLUMN_ANSWER_VALUE, task.getAnswerValue());
            return database.update(TABLE_NAME, contentValues,
                    COLUMN_ID + " = ?", // изменяем где id == нашему
                    new String[]{ String.valueOf( task.getId()) });
        }

        public void delete(long id) {
            database.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{ String.valueOf(id) });
        }

        public void deleteAll(long lessonId) {
            database.delete(TABLE_NAME, COLUMN_LESSON_ID + " = ?", new String[]{ String.valueOf(lessonId) });
        }

        // TABLE FIELDS!
        private static final String TABLE_NAME = "count_tasks";

        private static final String COLUMN_ID = "id";
        private static final String COLUMN_LESSON_ID = "lesson_id";
        private static final String COLUMN_TEXT = "text";
        private static final String COLUMN_MAX_SCORE = "max_score";
        private static final String COLUMN_SCORE = "score";
        private static final String COLUMN_STATE = "state";
        private static final String COLUMN_ANSWER_VALUE = "answer_value";

        private static final int NUM_COLUMN_ID = 0;
        private static final int NUM_COLUMN_LESSON_ID = 1;
        private static final int NUM_COLUMN_TEXT = 2;
        private static final int NUM_COLUMN_MAX_SCORE = 3;
        private static final int NUM_COLUMN_SCORE = 4;
        private static final int NUM_COLUMN_STATE = 5;
        private static final int NUM_COLUMN_ANSWER_VALUE = 6;
    }
}
