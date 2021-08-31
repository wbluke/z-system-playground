import React from "react";
import {
  Box,
  Button,
  Card,
  CardContent,
  FormControl,
  Grid,
  InputLabel,
  makeStyles,
  MenuItem,
  Select,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableRow,
  TextField
} from "@material-ui/core";

export interface IDocumentCategorySelectItem {
  value: string
  text: string
}

export interface IDocumentPageParams {
  category: string
  title: string
  contents: string
}

interface IDocumentPage {
  params: IDocumentPageParams
  setParams: (params: IDocumentPageParams) => void
  categorySelectItems: IDocumentCategorySelectItem[]
  onConfirm: () => void
}

const DocumentPage = (
  {
    params, setParams,
    categorySelectItems,
    onConfirm
  }: IDocumentPage) => {

  const classes = useStyles();

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
                  <li>결재자는 한 명 이상 지정해야 합니다.</li>
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
                      <TableCell className={classes.documentCategoryTableCell}>
                        <FormControl variant="outlined" fullWidth className={classes.documentCategory}>
                          <InputLabel id="document-category">문서 분류</InputLabel>
                          <Select
                            labelId="document-category"
                            value={params.category}
                            onChange={event => setParams({...params, category: event.target.value as string})}
                            label="문서 분류"
                          >
                            {
                              categorySelectItems.map((selectItem, index) => {
                                return <MenuItem key={index} value={selectItem.value}>{selectItem.text}</MenuItem>
                              })
                            }
                          </Select>
                        </FormControl>
                      </TableCell>
                      <TableCell className={classes.documentTableCell}>
                        <TextField
                          value={params.title}
                          onChange={event => setParams({...params, title: event.target.value})}
                          required
                          fullWidth
                          label="문서 제목"
                          variant="outlined"
                        />
                      </TableCell>
                    </TableRow>
                    <TableRow>
                      <TableCell className={classes.documentTableCell} colSpan={2}>
                        <TextField
                          value={params.contents}
                          onChange={event => setParams({...params, contents: event.target.value})}
                          required
                          fullWidth
                          multiline
                          label="본문"
                          rows={15}
                          variant={"outlined"}
                        />
                      </TableCell>
                    </TableRow>
                  </TableBody>
                </Table>
              </TableContainer>

            </CardContent>
          </Card>

          <div className={classes.confirmButton}>
            <Button
              variant="contained"
              color="primary"
              onClick={onConfirm}
            >
              문서 생성
            </Button>
          </div>

        </Grid>

      </Grid>
    </>
  );
}

const useStyles = makeStyles(theme => ({
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
    padding: '10px',
  },
  documentCategoryTableCell: {
    textAlign: 'center',
    border: '1px solid rgba(68, 157, 222, 0.3)',
    padding: '10px',
    width: '200px',
  },
  documentCategory: {
    minWidth: '120px',
  },
  confirmButton: {
    textAlign: 'right',
    padding: '10px',
  }
}));


export default DocumentPage;