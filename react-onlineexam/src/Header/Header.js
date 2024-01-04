import { useEffect, useState } from "react";
import Image1 from "../Header/images.png";
import 'bootstrap/dist/css/bootstrap.css';
import { useNavigate } from "react-router-dom";
const name = "Flash";
const currDate = new Date().toLocaleDateString();
const currTime = new Date().toLocaleTimeString();
const Header = ({ name, flag, setFlag, setName }) => {
  const username = name;
  console.log("Header username", username);
  const navigate = useNavigate();
  const onLogout = () => {
    setName('');
    setFlag(false);
    fetch('https://localhost:8443/onlineexam/control/logoutEvent', {
      method: 'GET',
      credentials: "include",
      headers: {
        'Content-type': 'application/json',
      }
    })
      .then(response => response.json())
      .then(data => {
        console.log("data", data);
        if (data.isLogout === true) {
          navigate('/');
          console.log("isFlag", flag);
        }
      })
    // console.log("isFlag", flag);
  }
  const [time, setTime] = useState({
    minutes: new Date().getMinutes(),
    hours: new Date().getHours(),
    seconds: new Date().getSeconds()
  })
  useEffect(() => {
    const intervalId = setInterval(() => {
      const date = new Date();
      setTime({
        minutes: date.getMinutes(),
        hours: date.getHours(),
        seconds: date.getSeconds()
      })
    }, 1000)
    return () => clearInterval(intervalId);
  }, [])
  const convertToTwoDigit = (number) => {
    return number.toLocaleString('en-IN', {
      minimumIntegerDigits: 2
    })
  }
  return (
    <div>
      <header className="header">
      </header>
      <nav className="navbar navbar-expand-lg bg-primary  navbar-dark py-3">
        <div className="container">
          <img className="img1" src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRFUyjXzzx6DUuFOdld6t7ToxT__gUxxkjfOPv-nNuvayL4L3jmQuQ2r6EIskGYFZ715UY&usqp=CAU" height={50} alt="" />
          <p className="navbar-brand offset-1 mt-2">NPM TEAM</p>
          <p className="navbar-brand offset-1 mt-2 offset-2 fs-15">ONLINE EXAMINATION</p>
          <div className="collapse navbar-collapse" id="navmenu">
            <ul className="navbar-nav ms-auto">
              <li className="nav-item">
                {name && <h3 className="text-light mt-3">Hello {name}!! </h3>}
                {flag && <button className="btn btn-outline-light" onClick={onLogout}>Logout</button>}
                {/* <p>{currTime}</p> */}
              </li>
            </ul>
          </div>
          {/* <div className='clock offset-'>
            <span className="text-light">{convertToTwoDigit(time.hours)}:</span>
            <span className="text-light">{convertToTwoDigit(time.minutes)}:</span>
            <span className="text-light">{convertToTwoDigit(time.seconds)}</span>
            <span className="text-light">{time.hours >= 12 ? ' PM' : ' AM'}</span>
          </div> */}
          {/* <p className="p1">{currTime}</p> */}
        </div>
      </nav>
    </div>
  );
};
export default Header;
