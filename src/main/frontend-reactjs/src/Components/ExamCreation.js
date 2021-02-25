import React from "react";
import { useState } from "react";
import Question from "./Question.js";
import DateFnsUtils from "../../node_modules/@date-io/date-fns"; // choose your lib
import { DateTimePicker, MuiPickersUtilsProvider } from "@material-ui/pickers";
import "../App.css";
import Button from "@material-ui/core/Button";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogContentText from "@material-ui/core/DialogContentText";
import DialogTitle from "@material-ui/core/DialogTitle";
import axios from "axios";
import TextField from "@material-ui/core/TextField";
import { makeStyles } from "@material-ui/core/styles";
import { Card } from "@material-ui/core";
import DeleteIcon from "@material-ui/icons/Delete";
import AddCircleOutlineIcon from "@material-ui/icons/AddCircleOutline";

const useStyles = makeStyles((theme) => ({
  root: {
    display: "flex",
    flexWrap: "wrap",
  },
  textField: {
    marginLeft: theme.spacing(1),
    marginRight: theme.spacing(1),
    width: "25ch",
  },
  date: {
    marginLeft: theme.spacing(10),
  },
  button: {
    marginLeft: theme.spacing(16),
  },

  card: {
    marginTop: theme.spacing(5),
  },
}));

const ExamCreation = (props) => {
  const [examTitle, setExamTitle] = useState("");
  const [number, setNumber] = useState(1);
  const [index, setIndex] = useState(0);
  const [selectedStartDate, handleDateChange] = useState(new Date());
  const [selectedEndDate, handleEndDateChange] = useState(new Date());
  const [examUrl, setUrl] = useState("");
  const [titles, setTitles] = useState(["lala"]); // holds question title states: String
  const [questions, setQuestions] = useState([]); // hold question component and its subcomponents states
  const [choices, setChoices] = useState([]); // holds checkbox states: false or true
  const [choiceTexts, setText] = useState([]); //holds choice texts states : String

  const classes = useStyles();
  const [open, setOpen] = useState(false);

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  function addQuestion() {
    setNumber(() => number + 1);
    setIndex(() => index + 1);
    setQuestions(() => [
      ...questions,
      [
        <Card className={classes.card}>
          <div> {number} </div>
          <Question
            className={classes.question}
            index={index}
            titleChange={questionOnChange}
            addChoiceState={addChoiceState}
            deleteChoiceState={deleteChoiceState}
            checkboxOnChange={checkboxOnChange}
            choiceTextOnChange={choiceTextOnChange}
          />
        </Card>,
      ],
    ]);

    setChoices(() => {
      let newChoices = choices;
      newChoices.push([]);
      newChoices[index].push(false);
      return newChoices;
    });

    setText(() => {
      choiceTexts.push([]);
      return choiceTexts;
    });
  }

  const decreaseQuestion = () => {
    if (number > 0) {
      setNumber(() => number - 1);

      setQuestions(() => questions.slice(0, index - 1));
      setIndex(() => index - 1);
      setTitles(() => titles.slice(0, index - 1));
      setChoices(() => {
        choices.pop();
        return choices;
      });
      setText(() => {
        choiceTexts.pop();
        return choiceTexts;
      });
    }
  };

  function questionOnChange(event) {
    setTitles(() => {
      titles[index] = event.target.value;
      return titles;
    });
  }

  function addChoiceState() {
    setChoices(() => {
      choices[index].push(false);
      return choices;
    });
  }

  function deleteChoiceState(cIndex) {
    setChoices(() => {
      choices[index].pop();
      return choices;
    });

    setText(() => {
      choiceTexts[index].pop();
      return choiceTexts;
    });
  }

  function checkboxOnChange(cIndex) {
    setChoices(() => {
      choices[index][cIndex] = !choices[index][cIndex];
      return choices;
    });
  }

  function choiceTextOnChange(event, cIndex) {
    setText(() => {
      choiceTexts[index][cIndex] = event.target.value;
      return choiceTexts;
    });
  }

  const submitExam = () => {
    const questionList = [];
    for (let i = 0; i < number - 1; i++) {
      const title = titles[i];
      var qChoices = [];

      let length = choices[i].length;

      for (let j = 0; j < length; j++) {
        qChoices.push({
          description: choiceTexts[i][j],
          correct: choices[i][j],
        });
      }

      questionList.push({
        questionTitle: title,
        choices: qChoices,
      });
    }

    const examObject = {
      title: examTitle,
      startDate: selectedStartDate,
      endDate: selectedEndDate,
      questions: questionList,
    };

    const token = Buffer.from(
      props.credentials.username + ":" + props.credentials.password,
      "utf8"
    ).toString("base64");

    axios
      .post("api/exams ", examObject, {
        headers: {
          Authorization: `Basic ${token}`,
        },
      })
      .then((res) => {
        setUrl(res.data.examUrl);
      });

    handleClickOpen();
  };

  const examTitleChange = (event) => {
    setExamTitle(() => event.target.value);
  };

  return (
    <div id="examForm">
      <TextField
        id="filled-full-width"
        placeholder="Exam Title"
        helperText="You can enter the title of your exam that you will create"
        margin="normal"
        onChange={(event) => examTitleChange(event)}
      />

      <div>
        <MuiPickersUtilsProvider utils={DateFnsUtils}>
          <DateTimePicker
            label="Start Date"
            value={selectedStartDate}
            onChange={handleDateChange}
          />
          <DateTimePicker
            className={classes.date}
            label="End Date"
            value={selectedEndDate}
            onChange={handleEndDateChange}
          />
        </MuiPickersUtilsProvider>
      </div>

      <Button
        onClick={addQuestion}
        variant="contained"
        color="primary"
        startIcon={<AddCircleOutlineIcon />}
      >
        Add Question
      </Button>
      <Button
        onClick={decreaseQuestion}
        variant="contained"
        color="secondary"
        className={classes.button}
        startIcon={<DeleteIcon />}
      >
        Delete Question
      </Button>

      {questions}

      <Button variant="outlined" color="primary" onClick={submitExam}>
        Submit Exam
      </Button>
      <Dialog
        open={open}
        onClose={handleClose}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
      >
        <DialogTitle id="alert-dialog-title">
          {"Exam Link is created"}
        </DialogTitle>
        <DialogContent>
          <DialogContentText id="alert-dialog-description">
            Your exam Link: {examUrl} <br/
            You can invite students via this link. Please dont lost this link
            because we don't hold it in user interface. :)
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose} color="primary">
            Close
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
};

export default ExamCreation;
