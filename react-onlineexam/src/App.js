
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
import { useState } from "react";
import ViewUsers from "./User/ViewUsers";
import Welcome from "./Welcome/Welcome";
import Examsforuser from "./User/Examsforuser";

//This is AA
function App() {
  const [name,setName] = useState();
  const [flag,setFlag] = useState(false);
  return (
    <div className="App">
      <Header name={name} flag={flag} setName={setName} setFlag={setFlag}   />
        <Routes>
        <Route path="/" element={<Login setName={setName} setFlag={setFlag} flag={flag} />} />
        <Route path="register" element={<Register/>}/>
        <Route path="*" element={<NoMatch />} />
        <Route path="user" element={<User/>}/>
        <Route path="admin" element={<Admin/>}>
        <Route path="welcome" element={<Welcome/>} />
        <Route path='assignExam' element={<ViewUsers />} />
          
          {/* EXAMS */}
        <Route path="createExam" element={<CreateExamMaster/>}/>
       <Route path="editExam" element={<CreateExamMaster/>}/>
       <Route path="updateExam" element={<GetExams/>}/>

       <Route path="updateExam/examdetails/:examId/" element={<ExamTopicMappingView/>} />
       <Route path="updateExam/examdetails/:examId/:topicId" element={<ExamTopicMappingView/>} />
       <Route path="updateExam/examdetails/question-topicView/:topicId/:examId" element={<QuestionForTopicView/>}/>

        <Route path="/admin/updateExam/examdetails/question-topicView/view-questions/:questionId/:topicName/:examId" element={<DetailsOfQuestion/>}/>
       {/* TOPIC */}

      <Route path="/admin/assignExam/addExamForUser" element={<Examsforuser/>} />

       {/* QUESTION */}

        </Route>
      </Routes> 


      <Footer />
    </div>
  );
}

export default App;
