
import "./App.css";
import Header from "./Header/Header";
import Footer from "./Footer/Footer";
import { Routes, Route } from "react-router-dom";
import NoMatch from "./PageNotFound/PageNotFound";
import User from './User/User'
import Admin from "./ExamMaster/Admin";
import Login from "./Login/Login";
import CreateExamMaster from "./ExamMaster/CreateExamMaster";
import GetExams from "./ExamMaster/GetExams";
import Register from "./Register/Register";
import ExamTopicMappingView from "./ExamMaster/ExamTopicMappingView";
import QuestionForTopicView from "./QuestionMaster/QuestionForTopicView";
import DetailsOfQuestion from "./ExamMaster/DetailsOfQuestion";
import { useEffect, useState } from "react";
import ViewUsers from "./User/ViewUsers";
import Welcome from "./Welcome/Welcome";
import Examsforuser from "./User/Examsforuser";
import AddExamForUser from "./User/AddExamForUser";
import QuestionMaster from "./QuestionMaster/QuestionMaster";
import EditExam from "./ExamMaster/EditExam";
import ListOfExamsForUser from "./User/ListOfExamsForUser";
import { control, pluginName, port, protocol } from "./constants";
//import ExamsDashboard from "./User/ExamsDashboard";
import ListOfQuestionsForUser from "./User/ListOfQuestionsForUser";
import ViewUserExamDetails from "./ViewUserExamDetails";

//This is App.js
function App() {
  const [name, setName] = useState('');
  const [flag, setFlag] = useState(false);


  useEffect(() => {
    fetch(protocol+"://"+window.location.hostname+":"+port+pluginName+control+"/getPersonName", {
      method: 'GET',
      credentials: "include",
    }).then(response => response.json())
      .then(data => {
        console.log("data.........................>",data)
        setName(data.userNameLogin);
      }).catch((error) => console.log(error))
  }, [flag])


  return (
    <div className="App">
      <Header name={name} flag={flag} setName={setName} setFlag={setFlag} />
      <Routes>
        <Route path="/" element={<Login setName={setName} setFlag={setFlag} flag={flag} />} />
        <Route path="register" element={<Register />} />
        <Route path="*" element={<NoMatch/>} />
        <Route path="user" element={<User/>} />
        <Route path="admin" element={<Admin/>}>
          <Route path="welcome" element={<Welcome/>} />
          <Route path='assignExam' element={<ViewUsers />} />

          {/* EXAMS */}
          <Route path="createExam" element={<CreateExamMaster />} />
            <Route path="editExam/:examId" element={<EditExam />} /> 
          <Route path="updateExam" element={<GetExams />} />
            <Route path="/admin/updateExam/getUserDetails/:examId" element={<ViewUserExamDetails/>} />
          <Route path="updateExam/examdetails/:examId/" element={<ExamTopicMappingView />} />
          <Route path="updateExam/examdetails/:examId/:topicId" element={<ExamTopicMappingView />} />
          <Route path="updateExam/examdetails/question-topicView/:topicId/:examId" element={<QuestionForTopicView />} />
          <Route path="updateExam/examdetails/question-topicView/:TopicId" element={<QuestionForTopicView />} /> 

          <Route path="/admin/updateExam/examdetails/question-topicView/view-questions/:questionId/:topicName/:examId" element={<DetailsOfQuestion />} />
          {/* TOPIC */}

          <Route path="/admin/assignExam/addExamForUser/:partyId/:firstName" element={<AddExamForUser/>} />
          <Route path="/admin/assignExam/addExamForUser/:partyId/:examId" element={<AddExamForUser/>} />
      <Route path="/admin/assignExam/examsForUser/:partyId/:firstName" element={<Examsforuser/>}/>
      <Route path="/admin/updateExam/examdetails/question-topicView/add-questions/:topicId/:topicName" element={<QuestionMaster/>} />
      <Route path="/admin/updateExam/examdetails/question-topicView/edit-question/:questionId/:TopicId/:topicName/:examId" element={<QuestionMaster/>}/>

          {/* QUESTION */}

          {/* QUESTION */
          }
        </Route>

        <Route path="user" element={<User />} />
        {/* <Route path="/user/listexamsforuser" element={<ListOfExamsForUser />} /> */}
        {/* <Route path="/user/examdashboard" element={<ExamsDashboard/>} /> */}
        <Route path="/user/listexams" element={<ListOfExamsForUser />} />
        <Route path="/user/listquestions/:examId" element={<ListOfQuestionsForUser/>}/>


      </Routes>


      <Footer />
    </div>
  );
}

export default App;
