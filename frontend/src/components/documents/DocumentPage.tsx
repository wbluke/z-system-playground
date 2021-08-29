import React from "react";
import {
  Box,
  Card,
  CardContent,
  Grid, InputLabel,
  makeStyles, MenuItem, Select,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableRow,
  TextField
} from "@material-ui/core";

const DocumentPage = () => {

  const classes = useStyles();

  const [age, setAge] = React.useState('');
  const handleChange = (event: React.ChangeEvent<{ value: unknown }>) => {
    setAge(event.target.value as string);
  };

  const [value, setValue] = React.useState('');
  const handleChangeValue = (event: React.ChangeEvent<HTMLInputElement>) => {
    setValue(event.target.value);
  };

  return (
    <>
      <Grid container spacing={1}>

        <Grid item xs={12}>
          <Card elevation={0} className={classes.titleCard}>
            <Box p={2}>
              <div className={classes.title}>
                신규 결재 문서
              </div>
              <div className={classes.titleDescription}>
                <ul>
                  <li>새로운 결재 문서를 기안할 수 있습니다.</li>
                  <li>문서는 제목과 분류, 내용을 가집니다.</li>
                </ul>
              </div>
            </Box>
          </Card>
        </Grid>

        <Grid item xs={12}>
          <Card elevation={0} className={classes.documentCardRoot}>
            <CardContent className={classes.documentCardContents}>

              <TableContainer>
                <Table className={classes.documentTable} size="small">
                  <TableBody>
                    <TableRow>
                      <TableCell className={classes.documentTableCell}>
                        <InputLabel id="demo-simple-select-label">Age</InputLabel>
                        <Select
                          fullWidth
                          labelId="demo-simple-select-label"
                          value={age}
                          onChange={handleChange}
                        >
                          <MenuItem value={10}>Ten</MenuItem>
                          <MenuItem value={20}>Twenty</MenuItem>
                          <MenuItem value={30}>Thirty</MenuItem>
                        </Select>
                      </TableCell>
                      <TableCell className={classes.documentTableCell}>
                        <TextField className={classes.documentTitleTextField} required id="standard-required" label="문서 제목"/>
                      </TableCell>
                    </TableRow>
                    <TableRow>
                      <TableCell className={classes.documentTableCell} colSpan={2}>
                        <TextField
                          id="outlined-multiline-flexible"
                          label="본문"
                          fullWidth
                          multiline
                          rows={15}
                          value={value}
                          onChange={handleChangeValue}
                          variant={"outlined"}
                        />
                      </TableCell>
                    </TableRow>
                  </TableBody>
                </Table>
              </TableContainer>

            </CardContent>
          </Card>
        </Grid>

      </Grid>
    </>
  );
}

const useStyles = makeStyles({
  titleCard: {
    padding: '0 0 0 10px',
    backgroundColor: '#fafafa',
  },
  title: {
    fontFamily: 'BM_HANNA_11yrs_old',
    fontSize: '40px',
  },
  titleDescription: {},
  documentCardRoot: {
    minWidth: 275,
    backgroundColor: 'rgba(66, 151, 212, 0.08)',
  },
  documentCardContents: {
    padding: '8px',
    '&:last-child': {
      paddingBottom: '8px'
    }
  },
  documentTable: {
    minWidth: 650,
  },
  documentTableCell: {
    textAlign: 'center',
    border: '1px solid rgba(68, 157, 222, 0.3)',
  },
  documentTitleTextField: {
    width: '500px',
  }
});


export default DocumentPage;
