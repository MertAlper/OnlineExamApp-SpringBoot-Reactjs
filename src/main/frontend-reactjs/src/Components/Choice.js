import React from "react";
import TextField from "@material-ui/core/TextField";
import Checkbox from "@material-ui/core/Checkbox";
import { Box } from "@material-ui/core";
import { makeStyles } from "@material-ui/core/styles";

const useStyles = makeStyles((theme) => ({
  field: {
    width: "90%",
  },
}));

const Choice = (props) => {
  const classes = useStyles();

  return (
    <Box display="block">
      <Checkbox onChange={() => props.checkboxOnChange(props.choiceIndex)} />

      <TextField
        className={classes.field}
        size="small"
        id="filled-full-width"
        placeholder="Enter Choice"
        margin="normal"
        onChange={(event) => props.choiceTextOnChange(event, props.choiceIndex)}
      />
    </Box>
  );
};

export default Choice;
