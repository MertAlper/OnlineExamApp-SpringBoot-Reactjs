import { useState } from "react";
import Choice from "./Choice";
import TextField from "@material-ui/core/TextField";
import { Button } from "@material-ui/core";
import "../App.css";
import { makeStyles } from "@material-ui/core/styles";
import DeleteIcon from "@material-ui/icons/Delete";
import AddCircleOutlineIcon from "@material-ui/icons/AddCircleOutline";

const useStyles = makeStyles((theme) => ({
  addButton: {
    color: "white",

    marginRight: theme.spacing(1),
  },
  deleteButton: {
    color: "white",

    marginRight: "0px",
  },

  questionTitle: {
    width: "100%",
  },

  choice: {
    marginBottom: "0px",
    color: "green",
  },
}));

export default function Question(props) {
  const [number, setNumber] = useState(1);
  const classes = useStyles();

  const choices = [];
  for (let i = 0; i < number; i++) {
    choices[i] = (
      <Choice
        className={classes.choice}
        choiceIndex={i}
        choiceTextOnChange={props.choiceTextOnChange}
        checkboxOnChange={props.checkboxOnChange}
      />
    );
  }

  function addChoice() {
    setNumber(() => number + 1);
    props.addChoiceState();
  }

  const decreaseChoice = () => {
    if (number > 0) {
      setNumber(() => number - 1);
      props.deleteChoiceState();
    }
  };

  return (
    <div id="question">
      <TextField
        className={classes.questionTitle}
        id="filled-full-width"
        placeholder="Enter Question Title"
        margin="normal"
        onChange={props.titleChange}
      />

      <Button
        className={classes.addButton}
        onClick={addChoice}
        variant="contained"
        color="primary"
        startIcon={<AddCircleOutlineIcon />}
      >
        Add Choice
      </Button>
      <Button
        className={classes.deleteButton}
        onClick={decreaseChoice}
        variant="contained"
        color="secondary"
        startIcon={<DeleteIcon />}
      >
        Delete Choice
      </Button>

      {choices}
    </div>
  );
}
