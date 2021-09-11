import React from "react";
import {
  Box,
  Card,
  Grid,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow
} from "@material-ui/core";
import commonStyles from "../../../common/styles/CommonStyles";

export interface IOutboxDocument {
  id: number
  categoryText: string
  title: string
  approvalStateText: string
}

interface IDocumentOutboxPage {
  outboxDocuments: IOutboxDocument[]
}

const DocumentOutboxPage = (
  {
    outboxDocuments
  }: IDocumentOutboxPage) => {

  const commonStyleClasses = commonStyles();

  const results = ['a']

  return (
    <>
      <Grid container spacing={1}>

        <Grid item xs={12}>
          <Card elevation={0} className={commonStyleClasses.titleCard}>
            <Box p={2}>
              <div className={commonStyleClasses.title}>
                진행 문서함
              </div>
              <div className={commonStyleClasses.titleDescription}>
                <ul>
                  <li>내가 기안한 문서 중 결재 진행 중인 문서를 확인할 수 있습니다.</li>
                  <li>문서보기 버튼으로 상세 내용을 확인할 수 있습니다.</li>
                </ul>
              </div>
            </Box>
          </Card>
        </Grid>

        <Grid item xs={12}>
          <Box pt={1}>
            <TableContainer component={Paper}>
              <Table className={commonStyleClasses.resultTable} stickyHeader>
                <TableHead>
                  <TableRow>
                    <TableCell className={commonStyleClasses.resultTableHeadCell}>ID</TableCell>
                    <TableCell className={commonStyleClasses.resultTableHeadCell}>분류</TableCell>
                    <TableCell className={commonStyleClasses.resultTableHeadCell}>제목</TableCell>
                    <TableCell className={commonStyleClasses.resultTableHeadCell}>결재상태</TableCell>
                    <TableCell className={commonStyleClasses.resultTableHeadCell}>문서보기</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {
                    outboxDocuments.map((document, index) => (
                      <TableRow key={index}>
                        <TableCell className={commonStyleClasses.resultTableBodyCell}>{document.id}</TableCell>
                        <TableCell className={commonStyleClasses.resultTableBodyCell}>{document.categoryText}</TableCell>
                        <TableCell className={commonStyleClasses.resultTableBodyCell}>{document.title}</TableCell>
                        <TableCell
                          className={commonStyleClasses.resultTableBodyCell}>{document.approvalStateText}
                        </TableCell>
                        <TableCell className={commonStyleClasses.resultTableBodyCell}>

                        </TableCell>
                      </TableRow>
                    ))
                  }
                </TableBody>
              </Table>
            </TableContainer>
          </Box>
        </Grid>
      </Grid>
    </>
  );
}

export default DocumentOutboxPage;
