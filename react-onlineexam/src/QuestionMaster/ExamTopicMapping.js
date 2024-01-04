import React from "react";

const ExamTopicMapping = () => {



   function addAnotherRow(){
    console.log("Add another row called...");
    return(
                <div>
            <div className="mb-3">
              <label
                htmlFor="exampleInputEmail1"
                className="form-label float-start"
              >
                ExamId*:
              </label>
              <input
                type="text"
                id="examid"
                className="form-control "
                placeholder="ExamId * eg(1)"
                name="examId"
                // value={examid}
                // onChange={(e) => setexamid(e.target.value)}
              />
              <p id="p1" className="text-danger"></p>
            </div>
            <div className="mb-3">
              <label
                htmlFor="exampleInputEmail1"
                className="form-label float-start"
              >
                TopicId*:
              </label>
              <input
                type="text"
                id="examid"
                className="form-control "
                placeholder="TopicId * eg(1)"
                name="examId"
                // value={examid}
                // onChange={(e) => setexamid(e.target.value)}
              />
              <p id="p1" className="text-danger"></p>
            </div>
            <div className="mb-3">
              <label
                htmlFor="exampleInputEmail1"
                className="form-label float-start"
              >
                Percentage:
              </label>
              <input
                type="text"
                id="examid"
                className="form-control "
                placeholder="Percentage * eg(100%)"
                name="examId"
                // value={examid}
                // onChange={(e) => setexamid(e.target.value)}
              />
              <p id="p1" className="text-danger"></p>
            </div>
            <div className="mb-3">
              <label
                htmlFor="exampleInputEmail1"
                className="form-label float-start"
              >
              TopicPassPercentage:
              </label>
              <input
                type="text"
                id="examid"
                className="form-control "
                placeholder="Topic Pass Percentage * eg(25%)"
                name="examId"
                // value={examid}
                // onChange={(e) => setexamid(e.target.value)}
              />
              <p id="p1" className="text-danger"></p>
            </div>
            <div className="mb-3">
              <label
                htmlFor="exampleInputEmail1"
                className="form-label float-start"
              >
                QuestionsPerExam:
              </label>
              <input
                type="text"
                id="examid"
                className="form-control"
                placeholder="Questions per Exam * eg(60)"
                name="examId"
                // value={examid}
                // onChange={(e) => setexamid(e.target.value)}
              />
              <p id="p1" className="text-danger"></p>
            </div>
        </div>
    )
   }
  return (
    <div className="container my-3">
      <div className="col-10">
        <form action="" >
            <div className="mb-3">
              <label
                htmlFor="exampleInputEmail1"
                className="form-label float-start"
              >
                ExamId*:
              </label>
              <input
                type="text"
                id="examid"
                className="form-control "
                placeholder="ExamId * eg(1)"
                name="examId"
                value="Tamil Exam"
                readOnlyd
                // value={examid}
                // onChange={(e) => setexamid(e.target.value)}
              />
              <p id="p1" className="text-danger"></p>
            </div>
            <div className="mb-3">
              <label
                htmlFor="exampleInputEmail1"
                className="form-label float-start"
              >
                TopicId*:
              </label>
              <input
                type="text"
                id="examid"
                className="form-control "
                placeholder="TopicId * eg(1)"
                name="examId"
                // value={examid}
                // onChange={(e) => setexamid(e.target.value)}
              />
              <p id="p1" className="text-danger"></p>
            </div>
            <div className="mb-3">
              <label
                htmlFor="exampleInputEmail1"
                className="form-label float-start"
              >
                Percentage:
              </label>
              <input
                type="text"
                id="examid"
                className="form-control "
                placeholder="Percentage * eg(100%)"
                name="examId"
                // value={examid}
                // onChange={(e) => setexamid(e.target.value)}
              />
              <p id="p1" className="text-danger"></p>
            </div>
            <div className="mb-3">
              <label
                htmlFor="exampleInputEmail1"
                className="form-label float-start"
              >
              TopicPassPercentage:
              </label>
              <input
                type="text"
                id="examid"
                className="form-control "
                placeholder="Topic Pass Percentage * eg(25%)"
                name="examId"
                // value={examid}
                // onChange={(e) => setexamid(e.target.value)}
              />
              <p id="p1" className="text-danger"></p>
            </div>
            <div className="mb-3">
              <label
                htmlFor="exampleInputEmail1"
                className="form-label float-start"
              >
                QuestionsPerExam:
              </label>
              <input
                type="text"
                id="examid"
                className="form-control"
                placeholder="Questions per Exam * eg(60)"
                name="examId"
                // value={examid}
                // onChange={(e) => setexamid(e.target.value)}
              />
              <p id="p1" className="text-danger"></p>
            </div>
            <div className="mb-3">
            
            <button className="btn btn-primary " onClick={addAnotherRow()}>Add Question</button>

            <button className="btn btn-primary ">Finish</button>
            </div>
          
        </form>
      </div>
    </div>
  );
};

export default ExamTopicMapping;
