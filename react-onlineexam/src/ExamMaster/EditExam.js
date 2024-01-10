import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import useStateRef from "react-usestateref";

const EditExam = () => {
  const params = useParams();
  const examId = params.examId;
  const [formValues, setFormValues] = useState([]);
  const [examname, setexamname, hasExamName] = useStateRef("");
  const [description, setdescription, hasDescription] = useStateRef("");
  const [creationdate, setcreationdate, hasCreatetionDate] = useStateRef("");
  const [expirationdate, setexpirationdate] = useState("");
  const [noofquestions, setnoofquestions] = useState("");
  const [durationminutes, setdurationminutes] = useState("");
  const [passpercentage, setpasspercentage] = useState("");
  const [questionsrandomized, setquestionsrandomized] = useState("");
  const [answersmust, setanswersmust] = useState("");
  const [enablenegativemark, setenablenegativemark] = useState("");
  const [negativemarkvalue, setnegativemarkvalue] = useState("");

  console.log("ExamId....>", examId);

  const navigate=useNavigate();

  useEffect(() => {
    let map = {
      examId: examId,
    };

    console.log("useeffect called...>", examId);

    fetch("https://localhost:8443/onlineexam/control/examMasterListEvent", {
      method: "POST",
      credentials: "include",
      body: JSON.stringify(map),
      headers: {
        "Content-type": "application/json",
      },
    })
      .then((response) => {
        return response.json();
      })
      .then((data) => {
        const text = data.ExamData;
        console.log("text...>", text);
        console.log("examname...>", text.examName);
        console.log("examId....>", text.examId);
        console.log("State is ...>", formValues);
        text.map((obj, value) => {
          console.log("examname...>", examname);
          setexamname(obj.examName);
          setdescription(obj.description);
          setcreationdate(obj.creationDate);
          setexpirationdate(obj.expirationDate);
          setnoofquestions(obj.noOfQuestions);
          setanswersmust(obj.answersMust);
          setdurationminutes(obj.durationMinutes);
          setpasspercentage(obj.passPercentage);
          setquestionsrandomized(obj.questionsRandomized);
          setenablenegativemark(obj.enableNegativeMark);
          setnegativemarkvalue(obj.negativeMarkValue);
        });
        // document.getElementById('modalId').click();
      });
  }, [examId]);

  return (
    <div className="my-5">
      <div className="col-10 modal-body">
        <form className="d-flex" id="myform">
          <div className="col-5">
            <div className="mb-3">
              <label
                htmlFor="exampleInputEmail1"
                className="form-label float-start"
                hidden
              >
                ExamId*:
              </label>
              <input
                type="text"
                id="examid"
                className="form-control "
                name="examId"
                placeholder="ExamId eg-1"
                //   value={obj.examId}
                hidden
                // onChange={(e)=>setFormValues.examId(e.target.value)}
              />
              <p id="p1" className="text-danger"></p>
            </div>
            <div className="mb-3">
              <label
                htmlFor="exampleInputEmail1"
                className="form-label float-start"
              >
                Exam Name:
              </label>
              <input
                type="text"
                id="examname"
                className="form-control"
                name="examName"
                placeholder="examName"
                value={examname}
                onChange={(e) => setexamname(e.target.value)}
              />
              <p id="p2" className="text-danger"></p>
            </div>
            <div className="mb-3">
              <label
                htmlFor="exampleInputEmail1"
                className="form-label float-start"
              >
                Description:
              </label>
              <input
                type="text"
                id="description"
                className="form-control"
                placeholder="Description"
                name="description"
                value={description}
                onChange={(e) => setdescription(e.target.value)}
                // defaultValue={description}
              />
              <p id="p3" className="text-danger"></p>
            </div>
            <div className="mb-3">
              <label htmlFor="" className="form-label float-start">
                Creationdate:
              </label>
              <input
                type="date"
                id="creationdate"
                className="form-control"
                name="creationDate"
                value={creationdate}
                onChange={(e) => setcreationdate(e.target.value)}
              />
              <p id="p4" className="text-danger"></p>
            </div>
            <div className="mb-3">
              <label
                htmlFor="exampleInputEmail1"
                className="form-label float-start"
              >
                Expiration Date:
              </label>
              <input
                type="date"
                id="expirationdate"
                className="form-control"
                name="expirationDate"
                value={expirationdate}
                onChange={(e) => setexpirationdate(e.target.value)}
              />
              <p id="p5" className="text-danger"></p>
            </div>
            <div className="mb-3">
              <label
                htmlFor="exampleInputPassword1"
                className="form-label float-start"
              >
                NoOfQuestions:
              </label>
              <input
                type="text"
                id="noofquestions"
                className="form-control"
                placeholder="No of questions"
                name="noOfQuestions"
                value={noofquestions}
                onChange={(e) => setnoofquestions(e.target.value)}
              />
              <p id="p6" className="text-danger"></p>
            </div>
            {/* <button className="btn btn-primary my-5" onClick={navigate(`/updateExam`)}>Back</button> */}
          </div>

          <div className="col-5 offset-1">
            <div className="mb-3">
              <label
                htmlFor="exampleInputEmail1"
                className="form-label float-start"
              >
                Duration Minutes:
              </label>
              <input
                type="text"
                id="durationminutes"
                placeholder="Duration Minutes eg(60-Minutes)"
                name="durationMinutes"
                className="form-control"
                value={durationminutes}
                onChange={(e) => setdurationminutes(e.target.value)}
              />
              <p id="p7" className="text-danger"></p>
            </div>
            <div className="mb-3">
              <label
                htmlFor="exampleInputEmail1"
                className="form-label float-start"
              >
                Passpercentage:
              </label>
              <input
                type="text"
                id="passpercentage"
                placeholder="pass-percentage eg(25%)"
                name="passPercentage"
                className="form-control"
                value={passpercentage}
                onChange={(e) => setpasspercentage(e.target.value)}
              />
              <p id="p8" className="text-danger"></p>
            </div>
            <div className="mb-3">
              <label
                htmlFor="exampleInputEmail1"
                className="form-label float-start"
              >
                QuestionsRandomized:
              </label>
              <select name="questionsRandomized" id="" className="form-select">
                {" "}
                QuestionsRandomized:
                <option value="Y">Yes</option>
                <option value="N">No</option>
              </select>
              <p id="p9" className="text-danger"></p>
            </div>
            <div className="mb-3">
              <label
                htmlFor="exampleInputEmail1"
                className="form-label float-start"
              >
                Answers Must:
              </label>
              <select name="answersMust" id="" className="form-select">
            
                QuestionsRandomized:
                <option value="Y">Yes</option>
                <option value="N">No</option>
              </select>
              <p id="p10" className="text-danger"></p>
            </div>
            <div className="mb-3">
              <label
                htmlFor="exampleInputEmail1"
                className="form-label float-start"
              >
                EnableNegativeMark:
              </label>
              <select name="enableNegativeMark" id="" className="form-select">
               
                QuestionsRandomized:
                <option value="Y">Yes</option>
                <option value="N  ">No</option>
              </select>
              <p id="p11" className="text-danger"></p>
            </div>
            <div className="mb-3">
              <label
                htmlFor="exampleInputEmail1"
                className="form-label float-start"
              >
                NegativeMarkValue:
              </label>
              <input
                type="text"
                placeholder="Negativemarkvalue eg(0.25)"
                id="negativemarkvalue"
                name="negativeMarkValue"
                className="form-control"
                value={negativemarkvalue}
                onChange={(e) => setnegativemarkvalue(e.target.value)}
              />
              <p id="p12" className="text-danger"></p>
            </div>

            <div>
              <button className="btn btn-primary offset-9">Submit</button>
            </div>
          </div>
        </form>
      </div>
    </div>
  );
};

export default EditExam;
