import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import useStateRef from "react-usestateref";

const ExamTopicMappingView = () => {
  const params = useParams();
  const examId = params.examId;
  const topicId = params.topicId;
  const [data, setData] = useState("");
  const [errMsg,setErrMsg]=useState("");
  const [topicData, setTopicData] = useState([]);
  const [Examname, SetexamName] = useState("");
  const [hasError,setHasError,hasNoError]=useStateRef(false);
  const [Percentage,setPercentage]=useState(false);
  var ExamName=Examname;
  var percentage;

  //showQuestionsForTopic is used to display the questions for that particular topic and which accepts the topic id,examid and it can navigate that particular component.
  //getExamTopicDetails is used to retrieve the particular topic details with the topic Id and examId 

  const navigate = useNavigate();
  const showQuestionsForTopic = (topicId, examId) => {
    navigate(
      `/admin/updateExam/examdetails/question-topicView/${topicId}/${examId}`
    );
  };

 
  const getExamTopicDetails=(examId,topicId)=>{

    console.log("getExamTopicDetails called......");
    const map={
      examId : examId,
      topicId : topicId,
    }

    fetch(
      "https://localhost:8443/onlineexam/control/examTopicRetrieveEvent",
      {
        method: "POST",
        credentials: "include",
        body: JSON.stringify(map),
        headers: {
          "Content-Type": "application/json"
        },
        Accept: "application/json",
      }
    )
    .then(response=>{
      return response.json();
    })
    .then(data=>{
      console.log("data",data);
      console.log(data.resultMap.examId);
      setData(data.resultMap);
    }).catch(err =>console.log(err));

   
  }

  const deleteTopic=(examId,topicId)=>{
    let map={
      examId : examId,
      topicId : topicId
    }
  
  fetch(
    "https://localhost:8443/onlineexam/control/deleteExamTopicMappingEvent",
    {
      method: "DELETE",
      credentials: "include",
      body: JSON.stringify(map),
      headers: {
        "Content-type": "application/json",
      },
    }
  ).then(response=>{
   
    return response.json();
    
  }).then(data=>{
    window.location.reload();
  })

  
};

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log("Handle submit called....>")
    const formElement = document.getElementById("form");
    const data = new FormData(e.target);
    handleErrors();
    console.log("examId-----", examId);
    // const topicId = data.get("topicId");
    const topicName = data.get("topicName");
    const percentage = data.get("percentage");
    const topicPassPercentage = data.get("topicPassPercentage");
    const questionsPerExam = data.get("questionsPerExam");
  
    const map = {
      examId: examId,
      topicId: topicId,
      topicName: topicName,
      percentage: percentage,
      topicPassPercentage: topicPassPercentage,
      questionsPerExam: questionsPerExam,
    };


    const validateExamTopicMappingMaster = (key, value) => {
      switch (key) {
        case "examId":
          if (value === null || value === "") {
            document.getElementById("p1").classList.remove("d-none");
            document.getElementById("p1").classList.add("d-block");
            document.getElementById("p1").innerHTML = "Please enter a examId";
            setHasError(true);
          }
          break;
        // case "topicId":
        //   if (value == "" || value == null) {
        //     document.getElementById("p2").classList.remove("d-none");
        //     document.getElementById("p2").classList.add("d-block");
        //     document.getElementById("p2").innerHTML = "Please enter a examName";
        //     setHasError(true);
        //   }
        //   break;
  
        case "topicName":
          if (value == "" || value == null) {
            document.getElementById("p3").classList.remove("d-none");
            document.getElementById("p3").classList.add("d-block");
            document.getElementById("p3").innerHTML =
              "Please enter a topicName";
            setHasError(true);
          }
  
        case "percentage":
          if (value == "" || value == null) {
            document.getElementById("p4").classList.remove("d-none");
            document.getElementById("p4").classList.add("d-block");
            document.getElementById("p4").innerHTML =
              "Please enter a percentage";
            setHasError(true);
          }
          break;
  
        case "topicPassPercentage":
          if (value == "" || value == null) {
            document.getElementById("p5").classList.remove("d-none");
            document.getElementById("p5").classList.add("d-block");
            document.getElementById("p5").innerHTML =
              "Please enter a topic pass percentage";
            setHasError(true);
          }
          break;

        case "questionsPerExam":
          if (value == "" || value == null) {
            document.getElementById("p6").classList.remove("d-none");
            document.getElementById("p6").classList.add("d-block");
            document.getElementById("p6").innerHTML =
              "Please enter a noof questions";
            setHasError(true);
          }
          break;
        default: {
          setHasError(false);
        }
      }
    };

    const myobject = Object.fromEntries(data.entries());
    Object.entries(myobject).map(([key, value], keyIndex) => {
      validateExamTopicMappingMaster(key, value);
    });    

    console.log("map.......>",map);

    if(!hasNoError.current){
      fetch(
        "https://localhost:8443/onlineexam/control/createOrUpdateExamTopicMappingMasterEvent",
        {
          method: "POST",
          credentials: "include",
          body: JSON.stringify(map),
          headers: {
            "Content-Type": "application/json"
          },
          Accept: "application/json",
        }
      )
        .then(response => 
        {
  
          return response.json();
        
        })
        .then(data => {
            console.log("my data.....",data);
            console.log("error msg=",);
            document.getElementById('err').classList.add('d-none');
            if(data.totalpercentage == 100){
              document.getElementById('add-btn').disabled=true;
            }
            if(data._ERROR_MESSAGE){
              document.getElementById('err').classList.remove('d-none');
              document.getElementById('err').classList.add('d-block')
              document.getElementById('err').innerHTML=data._ERROR_MESSAGE;
               
            }
            examDetails();
            
        })
        .catch(err=> console.log(err) );
    }

   
  };


  function handleErrors() {
    document.getElementById("p1").classList.add("d-none");
    // document.getElementById("p2").classList.add("d-none");
    document.getElementById("p3").classList.add("d-none");
    document.getElementById("p4").classList.add("d-none");
    document.getElementById("p5").classList.add("d-none");
    // document.getElementById("p6").classList.add("d-none");
    setHasError(false);
  }



  function examDetails() {
  
    let map = {
      examId: examId,
    };
    console.log("map....", map);
    fetch(
      "https://localhost:8443/onlineexam/control/getExamTopicEvent",
      {
        method: "POST",
        credentials: "include",
        body: JSON.stringify(map),
        headers: {
          "Content-type": "application/json",
        },
      }
    ).then(response=>{
      return response.json()})
      .then(data=>{
        console.log("data...",data);
       const output= data.ExamTopicNameMapping.ExamTopicMapping
       setTopicData(output);
       SetexamName(data.genericvalue.examName);
      })
  }
  useEffect(() => {
    examDetails();
    getExamTopicDetails();
  },[]);

  

  function showForm() {
    console.log("Show form called...");
    document.getElementById("myform").classList.remove("d-none");
    document.getElementById("myform").classList.add("d-block");
  }

  function hideForm() {
    console.log("Show form called...");
    document.getElementById("myform").classList.remove("d-none");
    document.getElementById("myform").classList.add("d-block");
  }

  return (
    <div className="container">
      
      <div className="container my-5">
      <h1 className="text text-primary d-none" align="center" id="err"></h1>
        <table className="table table-striped">
          <thead>
            <tr>
              <th>Exam Name</th>
              <th>Topic Name</th>
              <th>Topic Pass Percentage</th>
              <th>Percentage</th>
              <th>Questions Per Exam</th>
              <th>Edit</th>
              <th>View Question</th>
              <th>
                <button className="btn btn-primary" id="add-btn" onClick={() => showForm()} >
                  Add Topic
                </button>
              </th>
            </tr>
          </thead>
          <tbody>
            {topicData.length !== 0 ? (
              topicData.map((obj, value) => {
      
                return (
                  <tr >
                    <td>{obj.examName}</td>
                    <td>{obj.topicName}</td>
                    <td>{obj.topicPassPercentage}</td>
                    <td>{obj.percentage}</td>
                    <td>{obj.questionsPerExam}</td>
                    
                    <td>
                      <button className="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal" onClick={()=>getExamTopicDetails(obj.examId,obj.topicId)}>Edit</button>
                    </td>
                    <td>
                      <button
                        className="btn btn-primary"
                        onClick={() =>
                          showQuestionsForTopic(obj.topicId, obj.examId)
                        }
                      >
                        View
                      </button>
                    </td>
                    <td>
                      <button className="btn btn-danger" onClick={()=>deleteTopic(obj.examId,obj.topicId)} >Delete</button>
                    </td>
                  </tr>
                );
              })
            ) : (
              
              <h1 className="w-100" align="center">
                No topics to show
              </h1>
            
            )}
          </tbody>
        </table>
        <div>
          <button
            className="btn btn-primary"
            onClick={() => navigate("/admin/updateExam")}
          >
            Back
          </button>
        </div>
      </div>

      {/* ----------------add topic------------- */}

      <div>
        <div className="d-none" id="myform">
          
          <div className="col-10">
            <form onSubmit={handleSubmit} id="form">
              <div className="mb-3">
                <label
                  htmlFor="exampleInputEmail1"
                  className="form-label float-start"
                >
                  Exam Name:
                </label>
                <input
                  id="examid"
                  className="form-control "
                  placeholder="ExamId * eg(1)"
                  name="examName"
                  value={ExamName}
                  readOnly
                />
                <p id="p1" className="text-danger"></p>
              </div>

              {/* <div className="mb-3">
                <label
                  htmlFor="exampleInputEmail1"
                  className="form-label float-start"
                >
                  Topic No:
                </label>
                <input
                  type="text"
                  id="examid"
                  className="form-control "
                  placeholder="Topicid"
                  name="topicId"
                />
                <p id="p2" className="text-danger"></p>
              </div> */}

              <div className="mb-3">
                <label
                  htmlFor="exampleInputEmail1"
                  className="form-label float-start"
                >
                  Topic Name:
                </label>
                <input
                  type="text"
                  id="examid"
                  className="form-control "
                  placeholder="TopicName"
                  name="topicName"
                  // value={examid}
                  // onChange={(e) => setexamid(e.target.value)}
                />
                <p id="p3" className="text-danger"></p>
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
                  name="percentage"
                />
                <p id="p4" className="text-danger"></p>
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
                  name="topicPassPercentage"
                  
                />
                <p id="p5" className="text-danger"></p>
              </div>
              {/* <div className="mb-3">
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
                  name="questionsPerExam"
                
                />
                <p id="p6" className="text-danger"></p>
              </div> */}
              <div className="mb-3">
                <button className="btn btn-primary" onClick={() => hideForm()}>
                  Submit
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>


      {/* -------------------modal----------------- */}

<div class="modal fade" id="exampleModal" tabIndex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Edit topic</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          {/* <span aria-hidden="true">&times;</span> */}close
        </button>
      </div>
      <div class="modal-body">
      <form action="" onSubmit={handleSubmit} id="form">
              <div className="mb-3">
                <label
                  htmlFor="exampleInputEmail1"
                  className="form-label float-start"
                >
                  Exam Name:
                </label>
                <input
                  id="examid"
                  className="form-control "
                  placeholder="ExamId * eg(1)"
                  name="examName"
                  defaultValue={ExamName}
                  readOnly
                />
                <p id="p1" className="text-danger"></p>
              </div>

              <div className="mb-3">
                <label
                  htmlFor="exampleInputEmail1"
                  className="form-label float-start"
                >
                  Topic Id:
                </label>
                <input
                  type="text"
                  id="examid"
                  className="form-control"
                  placeholder="Topicid"
                  name="topicId"
                  defaultValue={data.topicId}
                  readOnly
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
                  name="percentage"
                  defaultValue={data.percentage}
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
                  name="topicPassPercentage"
                  defaultValue={data.topicPassPercentage}
      
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
                  name="questionsPerExam"
                  defaultValue={data.questionsPerExam}
                  // onChange={(e) => setData(e.target.value)}
                />
                <p id="p1" className="text-danger"></p>
              </div>
              <div className="mb-3">
                <button className="btn btn-primary offset-9" data-dismiss="modal">
                  Submit
                </button>
              </div>
            </form>
      </div>
     
    </div>
  </div>
</div>
    </div>
  );
};

export default ExamTopicMappingView;
