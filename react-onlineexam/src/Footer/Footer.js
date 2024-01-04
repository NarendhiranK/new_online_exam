// import "../Footer/Footer.css";

const Footer = () => {
  return (
    <footer
      style={{
        height: "10%",
        width: "100%",
        // position: "fixed",
        bottom: "0px",
        
      }}
    >
     <p className="lead text-white bg-primary py-5 " align="center">Copyright &copy; 2023 NPM TEAM</p>
      <a href="">
        <i className="bi bi-arrow-up-circle h1"></i>
      </a>
    </footer>
  );
};

export default Footer;
