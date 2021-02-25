import "./App.css";

import { BrowserRouter as Router } from "react-router-dom";
import { Switch, Route, Link } from "react-router-dom";
import SimpleTabs from "./Components/SimpleTabs";
import Exam from "./Components/Exam.js";
import ExamCreation from "./Components/ExamCreation.js";
import SignUp from "./Components/SignUp.js";
import Login from "./Components/Login.js";
import StudentLogin from "./Components/StudentLogin";
import { makeStyles } from "@material-ui/core/styles";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import IconButton from "@material-ui/core/IconButton";
import EditSharpIcon from "@material-ui/icons/EditSharp";
import ListAltSharpIcon from "@material-ui/icons/ListAltSharp";

const useStyles = makeStyles((theme) => ({
  root: {
    flexGrow: 1,
  },
  menuButton: {
    marginRight: theme.spacing(2),
  },
  logButton: {
    marginLeft: theme.spacing(120),
  },
}));

function App() {
  const classes = useStyles();

  return (
    <>
      <div className={classes.root}>
        <AppBar position="static">
          <Toolbar variant="dense">
            <IconButton
              edge="start"
              className={classes.menuButton}
              color="inherit"
              aria-label="menu"
            >
              <EditSharpIcon />
            </IconButton>
            <IconButton
              edge="start"
              className={classes.menuButton}
              color="inherit"
              aria-label="menu"
            >
              <ListAltSharpIcon />
            </IconButton>
            <Typography variant="h6" color="inherit">
              ONLINE EXAMINATION SYSTEM
            </Typography>
          </Toolbar>
        </AppBar>
      </div>

      <Router>
        <Switch>
          <Route exact path="/ExamCreation" component={ExamCreation} />
          <Route path="/instructor" component={SimpleTabs} />
          <Route exact path="/" component={Login}></Route>
          <Route
            exact
            path="/ExamLogin/:examToken"
            component={StudentLogin}
          ></Route>
          <Route path="/SignUp" component={SignUp}></Route>
          <Route path="/Exam" component={Exam} />
        </Switch>
      </Router>
    </>
  );
}

export default App;
