import React from "react";
import { Link, Outlet, useNavigate } from "react-router-dom";
import '../ExamMaster/Admin.css'

const Admin = () => {
  const navigate=useNavigate();
  return (
    <div className="container-fluid">
      <div className="row">
        <div className="col-2 h-100 offset-vh-100  min-vh-100 p-0 bg-primary">
          <div className=" flex-column justify-content-between col-auto bg-primary">
            <div className="mt-4">
              <a
                href=""
                className="text-white d-none d-sm-inline text-decoration-none d-flex align-items-center ms-4"
                role="button"
              >
                <i className="fa fa-users text-light"></i>
                <span className="fs-5">Admin</span>
              </a>
              <hr className="text-white d-none d-sm-block" />
              <ul className="nav nav-pills flex-column mt-2 mt-sm-0" id="menu">
                <li className="nav-item  my-sm-1 my-2 w-75 offset-1">
                  <a
                    href="#sidemenu"
                    data-bs-toggle="collapse"
                    className="nav-link bg-white  text-white"
                   
                  >
                    <i className="fa fa-edit text-primary"></i>
                    <span className="ms-2 d-none text-primary  d-sm-inline">
                      EXAMS
                    </span>
                    <i className="fa fa-caret-down text-primary"></i>
                  </a>
                  <ul
                    class="nav collapse  ms-1 flex-column"
                    id="sidemenu"
                    data-bs-parent="#menu"
                  >
                    <li class="nav-item my-sm-1 my-2">
                      <Link to="createExam"
                        class="nav-link  text-center  text-sm-start"   
                        aria-current="page"
                        // onClick={window.location.href=`createExam`}
                      >
                        Create Exam
                      </Link>
                    </li>
                    <li class="nav-item my-sm-1 my-2">
                      <Link  class="nav-link text-center text-sm-start" to="updateExam">
                        Update Exam
                      </Link>
                    </li>
                    {/* <li class="nav-item my-sm-1 my-2">
                      <a class="nav-link text-center text-sm-start" href="#">
                        Exam Details
                      </a>
                    </li>  */}
                    <li class="nav-item my-sm-1 my-2">
                      <Link  class="nav-link text-center text-sm-start" to="assignExam">
                        Assign Exam
                      </Link>
                    </li>
                  </ul>
                </li>
                {/* -------------------second drop down---------------- */}

              

                {/* ----------------third dropdown---------------- */}

  
              </ul>
            </div>
          </div>
        </div>
        <div className="col-10">
          
          <Outlet/>
        </div>
      </div>
    </div>
  );
};

export default Admin;
