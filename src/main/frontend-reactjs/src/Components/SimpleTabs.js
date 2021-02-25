import { useLocation } from "react-router-dom";
import axios from "axios";
import { useEffect, useState } from "react";
import { makeStyles } from "@material-ui/core/styles";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableContainer from "@material-ui/core/TableContainer";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import Paper from "@material-ui/core/Paper";
import PropTypes from "prop-types";
import AppBar from "@material-ui/core/AppBar";
import Tabs from "@material-ui/core/Tabs";
import Tab from "@material-ui/core/Tab";
import Typography from "@material-ui/core/Typography";
import Box from "@material-ui/core/Box";
import ExamCreation from "./ExamCreation.js";

const useStyles = makeStyles((theme) => ({
  table: {
    maxWidth: 300,
    marginTop: theme.spacing(1),
  },
  examTable: {
    marginLeft: theme.spacing(50),
    maxWidth: 600,
  },
  root: {
    flexGrow: 1,
    backgroundColor: theme.palette.background.paper,
  },
  container: {
    marginTop: theme.spacing(5),
  },
}));

function TabPanel(props) {
  const { children, value, index, ...other } = props;

  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`simple-tabpanel-${index}`}
      aria-labelledby={`simple-tab-${index}`}
      {...other}
    >
      {value === index && (
        <Box p={3}>
          <Typography>{children}</Typography>
        </Box>
      )}
    </div>
  );
}

TabPanel.propTypes = {
  children: PropTypes.node,
  index: PropTypes.any.isRequired,
  value: PropTypes.any.isRequired,
};

function a11yProps(index) {
  return {
    id: `simple-tab-${index}`,
    "aria-controls": `simple-tabpanel-${index}`,
  };
}

export default function SimpleTabs() {
  const classes = useStyles();
  const [value, setValue] = useState(0);
  const location = useLocation();
  const credentials = {
    username: location.state.credentials.username,
    password: location.state.credentials.password,
  };

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  return (
    <div className={classes.root}>
      <AppBar position="static">
        <Tabs
          value={value}
          onChange={handleChange}
          aria-label="simple tabs example"
        >
          <Tab label="Exam Results" {...a11yProps(0)} />
          <Tab label="Create Exam!" {...a11yProps(1)} />
        </Tabs>
      </AppBar>
      <TabPanel value={value} index={0}>
        <InstructorScreen credentials={credentials} />
      </TabPanel>
      <TabPanel value={value} index={1}>
        <ExamCreation credentials={credentials} />
      </TabPanel>
    </div>
  );
}

function InstructorScreen(props) {
  const classes = useStyles();
  const [examInfo, setExamInfo] = useState([]);
  const [attendance, setAttendance] = useState([]);
  const [loading, setLoading] = useState(true);

  const credentials = props.credentials;
  const authToken = Buffer.from(
    credentials.username + ":" + credentials.password,
    "utf8"
  ).toString("base64");

  const headers = {
    headers: {
      Authorization: `Basic ${authToken}`,
    },
  };

  useEffect(() => {
    axios
      .get(
        "api/exams/by/" + credentials.username,
        headers
      )
      .then((res) => {
        let examArray = [];
        let attendanceArray = [];
        let exams = res.data;

        exams.forEach((exam, index) => {
          examArray.push([exam.title, exam.startDate, exam.endDate]);
          let transfer = [];
          exam.attendanceList.forEach((at, i) => {
            transfer.push(at);
          });
          attendanceArray.push(transfer);
        });

        setExamInfo(() => examArray);
        setAttendance(() => attendanceArray);

        setLoading(false);
      });
  }, []);

  return loading ? (
    <h1>loading</h1>
  ) : (
    <div className={classes.examTable}>
      {examInfo.map((exam, index) => {
        if (JSON.stringify(attendance[index]) === JSON.stringify([]))
          return (
            <TableContainer className={classes.container} component={Paper}>
              <Typography
                component="h2"
                variant="h6"
                color="primary"
                gutterBottom
              >
                Exam Title: {exam[0]} <br />
                Start Date: {exam[1]} <br />
                End Date: {exam[2]} <br />
              </Typography>
              <TableRow>
                <TableCell>Nobody Attended to this exam yet</TableCell>
              </TableRow>
            </TableContainer>
          );
        else
          return (
            <>
              <TableContainer className={classes.container} component={Paper}>
                <Typography
                  component="h2"
                  variant="h6"
                  color="primary"
                  gutterBottom
                >
                  Exam Title: {exam[0]} <br />
                  Start Date: {exam[1]} <br />
                  End Date: {exam[2]} <br />
                </Typography>
                <Table
                  size="small"
                  className={classes.table}
                  aria-label="simple table"
                >
                  <TableHead>
                    Attendances:
                    <TableRow>
                      <TableCell>Student ID</TableCell>
                      <TableCell align="right">Attendance</TableCell>
                      <TableCell align="right">True Answer</TableCell>
                      <TableCell align="right">False Answer</TableCell>
                      <TableCell align="right">Points</TableCell>
                    </TableRow>
                  </TableHead>
                  <TableBody>
                    {attendance[index].map((attendance) => {
                      return (
                        <TableRow>
                          <TableCell component="th" scope="row">
                            {attendance.studentId}
                          </TableCell>
                          <TableCell align="right">
                            {String(attendance.attended)}
                          </TableCell>
                          <TableCell align="right">
                            {attendance.numOfTrues}
                          </TableCell>
                          <TableCell align="right">
                            {attendance.numOfFalses}
                          </TableCell>
                          <TableCell align="right">
                            {attendance.pointReceived}
                          </TableCell>
                        </TableRow>
                      );
                    })}
                  </TableBody>
                </Table>
              </TableContainer>
            </>
          );
      })}
    </div>
  );
}
