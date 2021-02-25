import { useEffect, useState } from "react";
import axios from "axios";
import { useHistory, useLocation } from "react-router-dom";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemText from "@material-ui/core/ListItemText";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import Checkbox from "@material-ui/core/Checkbox";
import Button from "@material-ui/core/Button";
import { makeStyles } from "@material-ui/core/styles";
import CheckCircleOutlineIcon from "@material-ui/icons/CheckCircleOutline";
import Typography from "@material-ui/core/Typography";
import { Card } from "@material-ui/core";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogContentText from "@material-ui/core/DialogContentText";
import DialogTitle from "@material-ui/core/DialogTitle";

const useStyles = makeStyles((theme) => ({
  root: {
    width: "50%",
    marginLeft: "25%",
    marginTop: "4%",
    // maxWidth: 360,
    overflow: "auto",
    // maxHeight: 500,
  },

  typ: {
    align: "justify",
  },

  child: {
    width: "100%",
    marginTop: "4%",
    backgroundColor: "rgb(204, 228, 255)",
    // maxWidth: 360,
  },

  submit: {
    marginTop: "1%",
    marginBottom: "1%",

    marginLeft: "38%",
  },
}));

function Exam() {

  let correct = 0;
  let wrong = 0;

  const location = useLocation();
  const examToken = location.state.token;
  const username = location.state.username;
  const password = location.state.password;
  const authToken = Buffer.from(username + ":" + password, "utf8").toString(
    "base64"
  );

  const headers = {
    headers: {
      Authorization: `Basic ${authToken}`,
    },
  };

  const classes = useStyles();

  const [open, setOpen] = useState(false);
  const [examData, setExamData] = useState({});
  const [examInfo, setExamInfo] = useState({});
  const [result, setResult] = useState({ true: "", false: "" });
  const [questions, setQuestions] = useState([]);
  const [choices, setChoices] = useState([]);
  const [choiceState, setChoiceState] = useState([]);

  const registerAndGetExam = async () => {
    await axios
      .get(
        "api/students/confirm?token=" + examToken,
        headers
      )
      .then((res) => {
        console.log(res.data);
      });

    axios
      .get("api/exams/token:" + examToken, {
        headers: {
          Authorization: `Basic ${authToken}`,
        },
      })
      .then((res) => {
        console.log(res.data);
        setExamInfo(res.data);
        setExamData(res.data.attendanceList.map((a) => a));
        let questionsArray = [];
        let choicesArray = [];

        let questions = res.data.questions;

        questions.forEach((question, index) => {
          questionsArray.push(question.questionTitle);
          let transfer = [];
          question.choices.forEach((choice, i) => {
            transfer.push(choice);
          });
          choicesArray.push(transfer);
        });

        setChoiceState(() => {
          let choiceStates = choicesArray.map((choice) =>
            choice.map((c) => false)
          );
          return choiceStates;
        });

        setQuestions(() => questionsArray);
        setChoices(() => choicesArray);
      });
  };

  useEffect(() => {
    registerAndGetExam();
  }, []);

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  function calculateScore() {
    correct = 0;
    wrong = 0;
    const trueAnswers = choices.map((choice) => choice.map((c) => c.correct));

    questions.forEach((question, index) => {
      if (choiceState[index].toString() === trueAnswers[index].toString())
        correct++;
      else wrong++;
    });

    const Attendance = examData[examData.length - 1];

    const postAttendance = {
      ...Attendance,
      numOfTrues: correct,
      numOfFalses: wrong,
      attended: true,
    };


    axios
      .post(
        "api/students/saveResult",
        postAttendance,
        headers
      )
      .then((res) => console.log(res));

    setResult({ true: correct, false: wrong });

    handleClickOpen();
  }


  return (
    <Card className={classes.root}>
      <Typography className={classes.typ} variant="h6" component="h1">
        Title: {examInfo.title}
      </Typography>
      <Typography className={classes.typ} variant="h6" component="h1">
        Start Date:{examInfo.startDate}
      </Typography>
      <Typography className={classes.typ} variant="h6" component="h1">
        End Date: {examInfo.endDate}
      </Typography>
      {choices.map((choice, i) => {
        const qIndex = i;
        console.log(examInfo);
        return (
          <Card className={classes.child}>
            {i + 1}. {questions[i]}
            <List>
              {choice.map((c, index) => {
                return (
                  <div>
                    <ListItem role={undefined} button>
                      <FormControlLabel
                        control={
                          <Checkbox
                            onChange={() =>
                              setChoiceState(() => {
                                console.log(choiceState);
                                choiceState[qIndex][index] = !choiceState[
                                  qIndex
                                ][index];
                                return choiceState;
                              })
                            }
                          />
                        }
                        tabIndex={-1}
                        disableRipple
                      />

                      <ListItemText primary={c.description} />
                    </ListItem>
                  </div>
                );
              })}
            </List>
          </Card>
        );
      })}
      <Button
        variant="contained"
        color="primary"
        className={classes.submit}
        startIcon={<CheckCircleOutlineIcon />}
        onClick={calculateScore}
      >
        Submit Exam
      </Button>
      <Dialog
        open={open}
        onClose={handleClose}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
      >
        <DialogTitle id="alert-dialog-title">
          {"Thanks for participating in exam."}
        </DialogTitle>
        <DialogContent>
          <DialogContentText id="alert-dialog-description">
            Correct Answers: {result.true} <br />
            Wrong Answers: {result.false}
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose} color="primary">
            Close
          </Button>
        </DialogActions>
      </Dialog>
      ;
    </Card>
  );
}

export default Exam;
