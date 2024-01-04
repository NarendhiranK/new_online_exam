import React from 'react'

const Examsforuser = () => {


    // fetch("https://localhost/8443/onlineexam/control/ExamsForUsersEvent").then(response=>{
    //     response.json();

    // }).then(data=>console.log(data)).catch(err=>console.log(err));
  return (
    <div>

<div className="row">
                <div className="col-2"></div>
                <div className="col-8">
                    <h1>UserExam Mapping: </h1>
                    <form id="myform">
                        <div className="form-group mt-3">
                            <label for="" className="form-label">
                                User ID
                            </label>
                            <input
                                type="text"
                                id="userId"
                                className="form-control "
                                
                                name="userId"
                            //value={examid}
                            //onChange={(e) => setexamid(e.target.value)}
                            />
                        </div>
                        <div className="form-group mt-3">
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
                        </div>
                        <div className="form-group mt-3">
                            <label for="" className="form-label ">
                                Allowed Attempts
                            </label>
                            <input
                                type="number"
                                id="allowedAttempts"
                                className="form-control   w-50"
                                placeholder="Enter total attempts"
                                name="allowedAttempts"
                            // value={description}
                            // onChange={(e) => setdescription(e.target.value)}
                            />
                        </div>

                        <div className="form-group mt-3">
                            <label for="" className="form-label ">
                                No of Attempts
                            </label>
                            <input
                                type="number"
                                id="noOfAttempts"
                                className="form-control  w-50"
                                placeholder="Enter the number of attempts by user"
                                name="noOfAttempts"
                            //value={creationdate}
                            //onChange={(e) => setcreationdate(e.target.value)}
                            />
                        </div>

                        <div className="form-group mt-3">
                            <label for="" className="form-label ">
                                Last Performance Date
                            </label>
                            <input
                                type="date"
                                id="lastPerformanceDate"
                                className="form-control  w-50"
                                name="lastPerformanceDate"
                            // value={expirationdate}
                            // onChange={(e) => setexpirationdate(e.target.value)}
                            />
                        </div>
                        <div className="form-group mt-3">
                            <label for="" className="form-label ">
                                Timeout Days
                            </label>
                            <input
                                type="text"
                                id="timeoutDays"
                                className="form-control   w-50"
                                placeholder="Enter the timeout days"
                                name="timeoutDays"
                            //value={noofquestions}
                            //  onChange={(e) => setnoofquestions(e.target.value)}
                            />
                        </div>

                        <div className="form-group mt-3">
                            <label for="" className="form-label ">
                                Password Changes Auto
                            </label>
                            <input
                                type="text"
                                id="passwordChangesAuto"
                                placeholder="Y or N"
                                name="passwordChangesAuto"
                                className="form-control  w-50"
                            //  value={durationminutes}
                            // onChange={(e) => setdurationminutes(e.target.value)}
                            />
                        </div>
                        <div className="form-group mt-3">
                            <label for="" className="form-label ">
                                Can Split Exams
                            </label>
                            <input
                                type="text"
                                id="canSplitExams"
                                placeholder="Y or N"
                                name="canSplitExams"
                                className="form-control   w-50"
                            // value={passpercentage}
                            //onChange={(e) => setpasspercentage(e.target.value)}
                            />
                        </div>
                        <div className="form-group mt-3">
                            <label for="" className="form-label ">
                                Can See Detailed Results
                            </label>
                            <input
                                type="text"
                                name="canSeeDetailedResults"
                                className="form-control"
                                id="canSeeDetailedResults"
                                placeholder="Y or N"
                            //  value={questionsrandomized}
                            //  onChange={(e) => setquestionsrandomized(e.target.value)}
                            />
                        </div>
                        <div className="form-group mt-3">
                            <label for="" className="form-label">
                                Max Split Attempts
                            </label>
                            <input
                                type="number"
                                name="maxSplitAttempts"
                                placeholder="Enter Max split attempts"
                                id="maxSplitAttempts"
                                className="form-control   w-50"
                            //  value={answersmust}
                            //  onChange={(e) => setanswersmust(e.target.value)}
                            />
                            {/* <select name="" type="dropdown" id=""></select> */}
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

                        <button type="submit" className="btn btn-primary mt-4" id="btn1">
                            Submit
                        </button>

                        {/* <button type="submit" className="btn btn-primary mt-4 d-none" id="btn2">
                            Update
                        </button>

                        <button type="submit" className="btn btn-primary mt-4 ml-5 d-none" id="btn3">
                            Add Topic
                        </button> */}
                    </form>
                </div>

                <div className="col-2"></div>
            </div>
    </div>
  )
}

export default Examsforuser
