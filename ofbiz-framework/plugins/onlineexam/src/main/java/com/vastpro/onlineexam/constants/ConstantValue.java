package com.vastpro.onlineexam.constants;

public interface ConstantValue {

	// Delegator and Dispatcher.
	String DELEGATOR = "delegator";
	String DISPATCHER = "dispatcher";

	// Messages.
	String ERROR = "error";
	String SUCCESS = "success";
	String ERROR_MESSAGE = "_ERROR_MESSAGE";
	String SUCCESS_MESSAGE = "_SUCCESS_MESSAGE";
	String RECORD_DELETE = "RECORD DELETED SUCCESSFULLY";
	String SERVICE_FAILED = "SERVICE FAILED";

	// Username and Password for "login" event.
	String USERNAME = "USERNAME";
	String PASSWORD = "PASSWORD";

	// ExamMaster table attributes.
	String EXAM_MASTER = "ExamMaster";
	String EXAM_ID = "examId";
	String EXAM_NAME = "examName";
	String DESCRIPTION = "description";
	String CREATION_DATE = "creationDate";
	String EXPIRATION_DATE = "expirationDate";
	String NO_OF_QUESTIONS = "noOfQuestions";
	String DURATION_MINUTES = "durationMinutes";
	String PASS_PERCENTAGE = "passPercentage";
	String QUESTIONS_RANDOMIZED = "questionsRandomized";
	String ANSWERS_MUST = "answersMust";
	String ENABLE_NEGATIVE_MARK = "enableNegativeMark";
	String NEGATIVE_MARK_VALUE = "negativeMarkValue";

	// Topic Master table attributes.
	String TOPIC_MASTER = "TopicMaster";
	String TOPIC_ID = "topicId";
	String TOPIC_NAME = "topicName";

	// Question Master table attributes.
	String QUESTION_MASTER = "QuestionMaster";
	String QUESTION_ID = "questionId";
	String QUESTION_DETAIL = "questionDetail";
	String OPTION_A = "optionA";
	String OPTION_B = "optionB";
	String OPTION_C = "optionC";
	String OPTION_D = "optionD";
	String OPTION_E = "optionE";
	String ANSWER = "answer";
	String NUM_ANSWERS = "numAnswers";
	String QUESTION_TYPE = "questionType";
	String DIFFICULTY_LEVEL = "difficultyLevel";
	String ANSWER_VALUE = "answerValue";

	// ExamTopicMappingMaster table attributes.
	String EXAM_TOPIC_MAPPING_MASTER = "ExamTopicMappingMaster";
	String PERCENTAGE = "percentage";
	String TOPIC_PASS_PERCENTAGE = "topicPassPercentage";
	String QUESTION_PER_EXAM = "questionsPerExam";

	// UserAttemptMaster table attributes.
	String USER_ATTEMPT_MASTER = "UserAttemptMaster";
	String PERFORMANCE_ID = "performanceId";
	String ATTEMPT_NUMBER = "attemptNumber";
	String PARTY_ID = "partyId";
	String SCORE = "score";
	String COMPLETED_DATE = "completedDate";
	String TOTAL_CORRECT = "totalCorrect";
	String TOTAL_WRONG = "totalWrong";
	String USER_PASSED = "userPassed";

	// UserAttemptTopicMaster table attributes
	String USER_ATTEMPT_TOPIC_MASTER = "UserAttemptTopicMaster";
	String TOTAL_QUESTIONS_IN_THIS_TOPIC = "totalQuestionsInThisTopic";
	String CORRECT_QUESTIONS_IN_THIS_TOPIC = "correctQuestionsInThisTopic";
	String USER_TOPIC_PERCENTAGE = "userTopicPercentage";
	String USER_PASSED_THIS_TOPIC = "userPassedThisTopic";

	// UserAttemptAnswerMaster table attributes
	String USER_ATTEMPT_ANSWER_MASTER = "UserAttemptAnswerMaster";
	String SEQUENCE_NUM = "sequenceNum";
	String SUBMITTED_ANSWER = "submittedAnswer";
	String IS_FLAGGED = "isFlagged";

	// UserExamMappingMaster table attributes
	String USER_EXAM_MAPPING_MASTER = "UserExamMappingMaster";
	String ALLOWED_ATTEMPTS = "allowedAttempts";
	String NO_OF_ATTEMPTS = "noOfAttempts";
	String LAST_PERFORMANCE_DATE = "lastPerformanceDate";
	String TIME_OUT_DAYS = "timeoutDays";
	String PASSWORD_CAHNGES_AUTO = "passwordChangesAuto";
	String CAN_SPLIT_EXAMS = "canSplitExams";
	String CAN_SEE_DETAILED_RESULTS = "canSeeDetailedResults";
	String MAX_SPLIT_ATTEMPTS = "maxSplitAttempts";

}
