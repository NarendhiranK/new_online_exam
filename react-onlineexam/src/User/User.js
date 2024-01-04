import React from "react";

const User = () => {
  return (
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
      <button type="submit" className="btn btn-primary offset-9">
        Next
      </button>
    </div>
  );
};

export default User;
