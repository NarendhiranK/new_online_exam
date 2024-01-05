import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

const AddExamForUser = () => {
  const navigate = useNavigate();
  const params = useParams();
  const partyId=params.partyId;
  const firstName = params.firstName;
  const ExamId=params.examId;
  console.log("This is examId...>",firstName);
  const [data, setData] = useState([]);



  const userExamSubmission=async (e)=>{
    e.preventDefault();
    const formElement = document.getElementById("myform");
    const data = new FormData(formElement);
    // const partyId=data.get("partyId");
    const examId=data.get("examId");
    const allowedAttempts=data.get("allowedAttempts");
    const noOfAttempts=data.get("noOfAttempts");
    const lastPerformanceDate=data.get("lastPerformanceDate");
    const timeoutDays=data.get("timeoutDays");
    const passwordChangesAuto=data.get("passwordChangesAuto");
    const canSplitExams=data.get("canSplitExams");
    const canSeeDetailedResults=data.get("canSeeDetailedResults");
    const maxSplitAttempts=data.get("maxSplitAttempts");

    let map={
        partyId: partyId,
        examId : examId,
        allowedAttempts : allowedAttempts,
        noOfAttempts : noOfAttempts,
        lastPerformanceDate : lastPerformanceDate,
        timeoutDays : timeoutDays,
        passwordChangesAuto : passwordChangesAuto,
        canSplitExams : canSplitExams,
        canSeeDetailedResults : canSeeDetailedResults,
        maxSplitAttempts : maxSplitAttempts

    }

    console.log("This is the map" , map);

  }
  function getExams() {
    fetch("https://localhost:8443/onlineexam/control/examMasterListEvent")
      .then((response) => {
        return response.json();
      })
      .then((data) => {
        setData(data.ExamData);
        const res = data.ExamData;
        setData([...res]);
        
       
        // const result = JSON.stringify(res);
        console.log("res....", res);
      });
  }

  useEffect(() => {
    getExams();
  }, []);

  return (
    <div>
      <div className="row">
        <div className="col-10 mx-5">
          <h3 className="my-5">UserExam Mapping: </h3>
          <form id="myform" className=" d-flex" onSubmit={userExamSubmission}>
            <div className="col-5">
            <div className="form-group mt-3">
              <label for="" className="form-label float-start" hidden>
                User ID
              </label>
              <input
                type="text"
                id="userId"
                className="form-control "
                hidden
                name="userId"
                //value={examid}
                //onChange={(e) => setexamid(e.target.value)}
              />
            </div>

            <div className="form-group mt-3">
              <label for="" className="form-label float-start">
                UserName
              </label>
              <input
                type="text"
                id="userId"
                className="form-control "
                value={firstName}
                readOnly
                name="userId"
                //value={examid}
                //onChange={(e) => setexamid(e.target.value)}
              />
            </div>
            {/* <div className="form-group mt-3">
                          <label for="" className="form-label ">
                              Exam ID
                          </label>
                          <input
                              type="text"
                              id="examId"
                              className="form-control  w-50"
                              
                              name="examId"
                          //value={examname}
                          // onChange={(e) => setexamname(e.target.value)}
                          />
                      </div> */}

            <div className="form-group mt-3">
                <label htmlFor="" className="form-label float-start">Select exam</label>
              <select name="examId" id="" className="form-select">
                Choose Exam
                {data
                  ? data.map((obj, value) => {
                      return <option value={obj.examId}>{obj.examName}</option>;
                    })
                  : ""}
              </select>
            </div>

            <div className="form-group mt-3">
              <label for="" className="form-label float-start">
                Allowed Attempts
              </label>
              <input
                type="number"
        
                className="form-control"
                placeholder="Enter total attempts"
                name="allowedAttempts"
                // value={description}
                // onChange={(e) => setdescription(e.target.value)}
              />
            </div>

            <div className="form-group mt-3">
              <label for="" className="form-label float-start">
                No of Attempts
              </label>
              <input
                type="number"
                id="noOfAttempts"
                className="form-control"
                placeholder="Enter the number of attempts by user"
                name="noOfAttempts"
                //value={creationdate}
                //onChange={(e) => setcreationdate(e.target.value)}
              />
            </div>

            <div className="form-group mt-3">
              <label for="" className="form-label float-start">
                Last Performance Date
              </label>
              <input
                type="date"
                id="lastPerformanceDate"
                className="form-control"
                name="lastPerformanceDate"
                // value={expirationdate}
                // onChange={(e) => setexpirationdate(e.target.value)}
              />
            </div>
            <button
        className="btn btn-primary my-3"
        onClick={() => {
          navigate("/admin/assignExam");
        }}
      >
        Back
      </button>
            </div>
            <div className="col-5 mx-5">
            <div className="form-group mt-3">
              <label for="" className="form-label float-start">
                Timeout Days
              </label>
              <input
                type="text"
                id="timeoutDays"
                className="form-control"
                placeholder="Enter the timeout days"
                name="timeoutDays"
                //value={noofquestions}
                //  onChange={(e) => setnoofquestions(e.target.value)}
              />
            </div>

            <div className="form-group mt-3">
              <label for="" className="form-label float-start">
                Password Changes Auto
              </label>
              {/* <input
                type="text"
                id="passwordChangesAuto"
                placeholder="Y or N"
                name="passwordChangesAuto"
                className="form-control"
                //  value={durationminutes}
                // onChange={(e) => setdurationminutes(e.target.value)}
              /> */}
              <select name="passwordChangesAuto" id="" className="form-select">Password changes auto
              <option value="Y">Yes</option>
              <option value="N">No</option>
              </select>
            </div>
            <div className="form-group mt-3">
              <label for="" className="form-label float-start">
                Can Split Exams
              </label>
              {/* <input
                type="text"
                id="canSplitExams"
                placeholder="Y or N"
                name="canSplitExams"
                className="form-label float-start"
                // value={passpercentage}
                //onChange={(e) => setpasspercentage(e.target.value)}
              /> */}
              <select name="canSplitExams" id="" className="form-select">Can split Exams
              <option value="Y">Yes</option>
              <option value="N">No</option>
              </select>
            </div>
            <div className="form-group mt-3">
              <label for="" className="form-label ">
                Can See Detailed Results
              </label>
              {/* <input
                type="text"
                name="canSeeDetailedResults"
                className="form-control"
                id="canSeeDetailedResults"
                placeholder="Y or N"
                //  value={questionsrandomized}
                //  onChange={(e) => setquestionsrandomized(e.target.value)}
              /> */}
              <select name="canSeeDetailedResults" id="" className="form-select">  Can See Detailed Results
              <option value="Y">Yes</option>
              <option value="N">No</option>
              </select>
            </div>
            <div className="form-group mt-3">
              <label for="" className="form-label float-start">
                Max Split Attempts
              </label>
              <input
                type="number"
                name="maxSplitAttempts"
                placeholder="Enter Max split attempts"
                id="maxSplitAttempts"
                className="form-control"
                //  value={answersmust}
                //  onChange={(e) => setanswersmust(e.target.value)}
              />
              {/* <select name="" type="dropdown" id=""></select> */}
            </div>
            <button
              type="submit"
              className="btn btn-primary mt-4 offset-9"
              id="btn1"
            >
              Submit
            </button>
            </div>
            {/* <div className="form-group mt-3">
                          <label for="" className="form-label">
                              Enablenegativemark
                          </label>
                          <input
                              type="text"
                              name="enableNegativeMark"
                              placeholder="Enablenegativemark eg(Y OR N)"
                              id="enablenegativemark"
                              className="form-control w-50"
                          //  value={enablenegativemark}
                          //  onChange={(e) => setenablenegativemark(e.target.value)}
                          />
                      </div>
                      <div className="form-group mt-3">
                          <label for="" className="form-label">
                              NegativeMarkvalue
                          </label>
                          <input
                              type="text"
                              placeholder="Negativemarkvalue eg(0.25)"
                              id="negativemarkvalue"
                              name="negativeMarkValue"
                              className="form-control  w-50"
                          // value={negativemarkvalue}
                          // onChange={(e) => setnegativemarkvalue(e.target.value)}
                          />
                      </div> */}

           

          </form>
         
        </div>
      </div>
    </div>
  );
};

export default AddExamForUser;
