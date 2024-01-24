import React from "react";
import { Link, Outlet, useNavigate } from "react-router-dom";

const User = () => {
  const navigate = useNavigate();
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
                <span className="fs-5">User</span>
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
                      <Link class="nav-link text-center text-sm-start" to="updateExam">
                        Update Exam
                      </Link>
                    </li>
                    {/* <li class="nav-item my-sm-1 my-2">
                    <a class="nav-link text-center text-sm-start" href="#">
                      Exam Details
                    </a>
                  </li>  */}
                    <li class="nav-item my-sm-1 my-2">
                      <Link class="nav-link text-center text-sm-start" to="assignExam">
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
        <div className="container-fluid px-5">
      <h2 className="text-center">Welcome to VastPRO's Online Examination</h2>
      <div
        className="container border border-5 overflow-y-auto d-inline-block"
        style={{ height: "350px" }}
      >
        <u>
          <h5 className="float-left">Guidelines to Online Examination for Students</h5>
        </u>
        <ol>
          <li className="float-left"> Arrange for stable Internet connectivity.</li>
          <li>

            Giving examination on Laptop or Desktop is highly recommended.
          </li>
          <li>

            Make sure mobile/laptop is fully charged. Power bank for mobile or
            UPS/Inverter for laptop/desktop should be arranged for uninterrupted
            power supply.
          </li>
          <li>

            Students should have sufficient data in Fair Usage Policy (FUP) /
            Internet plan with sufficient data pack of internet service
            provider.
          </li>
          <li>

            Login to the portal 10 min before the online examination start time.
          </li>
          <li>

            Close all browsers/tabs before starting the online examination
          </li>
          <li>

            Once the exam starts, do not switch to any other window/tab. On
            doing so, your attempt may be considered as malpractice and your
            exam may get terminated.
          </li>
          <li>

            Do Not Pickup/Receive the Call during the exam if you are giving the
            exam on mobile. This also will be treated as changing the window.
          </li>
          <li>

            Clear browser cache memory on mobile and laptops. Clear browsing
            history and also delete temp files.
          </li>
        </ol>
        <div>
          <u>
            <h6>Notes:</h6>
          </u>
          <ul>
            <li>
              It is recommended to use web browser such as Mozilla and Chrome
              browsers etc. on a desktop/laptop/tab/smart phone.
            </li>
            <li>
              Do not use the back button of keyboard or close button/icon to go
              back to previous page or to close the screen.
            </li>
            <li>
              Student will not allow to login after 30 min from the start of
              examination.
            </li>
          </ul>
        </div>
      </div>
      <button type="submit" className="btn btn-primary offset-9" onClick={() => navigate("/user/listexams")} >
        Next
      </button>
      <Outlet/>
    </div>
          <Outlet />
        </div>
      </div>
    </div>

  );
};

export default User;
