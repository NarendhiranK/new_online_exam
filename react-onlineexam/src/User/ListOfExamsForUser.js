import React, { useEffect, useState } from 'react'
import { control, pluginName, port, protocol } from '../constants';
import { useNavigate } from 'react-router-dom';

const ListOfExamsForUser = () => {
    const [examList, setExamList] = useState([]);
    const [userExamList, setUserExamList] = useState([]);

    const navigate=useNavigate();
    // const examDetails=(examId)=>{

    // }
    const takeExam=(examId)=>{

        let map={
            examId : examId
        }

        console.log("Map............",map);

        navigate(`/user/listquestions/${examId}`);
       
         
    }
    function examsList() {
        fetch("https://localhost:8443/onlineexam/control/examsForUserEvent", {
            method: "GET",
            credentials: "include",
            headers: {
                "Content-type": "application/json",
            },
        })
            .then(response => response.json())
            .then(data => {
                console.log("ListOfExamsForUser data =============>", data);
                setExamList(data.examDetailsResultList);
                setUserExamList(data.listOfExamsForUser);
                userExamList.map((obj,value)=>{
                    console.log("Obj values",obj);
                })
                console.log("The state is....",userExamList);
                console.log("allowed attemps",userExamList.allowedAttempts);

            }).catch((error) => console.log(error));
    }
    useEffect(() => {
        examsList()
    }, [])

    const examDetails = () => {

    }

    return (
        <div className='container'>
            <h2>List of Exams alloted for you : </h2>

            <table className="table-hover  w-75">
                <thead>
                    <tr className="bg-primary text-light">
                        <th>Exam Name</th>
                        <th>Duration Minutes</th>
                        <th>Expiration Date</th>
                        <th></th>
                        <th></th>
                        <td></td>
                    </tr>
                </thead>
                {examList.length !== 0 ? examList.map((exam) => {
                    return (
                        <tbody key={exam.perExamDetails.examId}>
                            <tr className="border border-dark">
                                <td>{exam.perExamDetails.examName}</td>
                                <td>{exam.perExamDetails.durationMinutes } minutes</td>
                                <td>{exam.perExamDetails.expirationDate}</td>
                                <td><button className="btn btn-primary" data-bs-target="#modalId" onClick={()=>examDetails(exam.perExamDetails.examId)} data-bs-toggle="modal">Details</button></td>
                                <td><button className="btn btn-primary" onClick={()=>takeExam(exam.perExamDetails.examId)}>Take Exam</button></td>

                            </tr>
                        </tbody>
                    );
                }) : "There are no exams assigned for you!!!"}
            </table>

            <div>
                <div class="modal" tabindex="-1" id='modalId'>
                    <div class="modal-dialog modal-xl">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title">Modal title</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">

                                <table className='table table-striped d-flex'>

                                    <div>
                                        <tr>
                                            <td>Exam Name</td>
                                            <td>Starting Date</td>
                                            <td>Ending Date</td>
                                            <td>Number of Questions</td>
                                            <td>Duration minutes</td>

                                        </tr>
                                    </div>
                                    <div>
                                        <tr>
                                            <td>Pass Percentage</td>
                                            <td>Answers must</td>
                                            <td>Negative Mark Value</td>
                                            <td>Allowed Attempts</td>
                                            <td>Number Of attempts</td>
                                        </tr>
                                    </div>
                                    {/* {examList && examList.map((exam,value) => {
                                        return (
                                            <>
                                                <div>
                                                    <tr>
                                                        <td>{exam.perExamDetails.examName}</td>
                                                        <td>Hello</td>
                                                        <td>Redundant</td>
                                                        <td>Redundant</td>
                                                        <td>Redundant</td>
                                                        
                                                    </tr>
                                                </div>
                                                <div>
                                                    <tr>
                                                        <td>Redundant</td>
                                                        <td>Redundant</td>
                                                        <td>Redundant</td>
                                                        <td>Redundant</td>
                                                        <td>Redundant</td>
                                                    </tr>
                                                </div>
                                            </>
                                        );
                                    })} */}

                                </table>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                            </div>
                        </div>
                    </div>
                </div>

            </div>


        </div>
    )
}

export default ListOfExamsForUser
