import React from "react";
import {
  Box,
  Button,
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
import {convertLocalDateTimeToString} from "../../../utils/localDateTimeUtils";
import DocumentApprovalModal, {IDocumentApprovalModalOpen} from "../DocumentApprovalModal";

export interface IInboxDocument {
  id: number
  categoryText: string
  title: string
  approvalStateText: string
  createdDateTime: string
}

interface IDocumentInboxPage {
  inboxDocuments: IInboxDocument[]
  documentModalOpen: IDocumentApprovalModalOpen
  setDocumentModalOpen: (modalOpen: IDocumentApprovalModalOpen) => void
}

const DocumentInboxPage = (
  {
    inboxDocuments, documentModalOpen, setDocumentModalOpen
  }: IDocumentInboxPage) => {

  const commonStyleClasses = commonStyles();

  return (
    <>
      <Grid container spacing={1}>

        <Grid item xs={12}>
          <Card elevation={0} className={commonStyleClasses.titleCard}>
            <Box p={2}>
              <div className={commonStyleClasses.title}>
                결재 문서함
              </div>
              <ul>
                <li>내가 결재해야 하는 문서를 확인할 수 있습니다.</li>
                <li>결재할 순서가 아닌 경우 결재를 진행할 수 없습니다.</li>
              </ul>
            </Box>
          </Card>
        </Grid>

        <Grid item xs={12}>
          <Box pt={1}>
            <TableContainer component={Paper}>
              <Table className={commonStyleClasses.resultTable} stickyHeader>
                <TableHead>
                  <TableRow>
                    <TableCell width="5%" className={commonStyleClasses.resultTableHeadCell}>ID</TableCell>
                    <TableCell width="7%" className={commonStyleClasses.resultTableHeadCell}>분류</TableCell>
                    <TableCell width="20%" className={commonStyleClasses.resultTableHeadCell}>생성시간</TableCell>
                    <TableCell width="30%" className={commonStyleClasses.resultTableHeadCell}>제목</TableCell>
                    <TableCell width="10%" className={commonStyleClasses.resultTableHeadCell}>결재상태</TableCell>
                    <TableCell width="10%" className={commonStyleClasses.resultTableHeadCell}>문서내용</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {
                    inboxDocuments.map((document, index) => (
                      <TableRow key={index}>
                        <TableCell className={commonStyleClasses.resultTableBodyCell}>
                          {document.id}
                        </TableCell>
                        <TableCell className={commonStyleClasses.resultTableBodyCell}>
                          {document.categoryText}
                        </TableCell>
                        <TableCell className={commonStyleClasses.resultTableBodyCell}>
                          {convertLocalDateTimeToString(document.createdDateTime)}
                        </TableCell>
                        <TableCell className={commonStyleClasses.resultTableBodyCell}>
                          {document.title}
                        </TableCell>
                        <TableCell className={commonStyleClasses.resultTableBodyCell}>
                          {document.approvalStateText}
                        </TableCell>
                        <TableCell className={commonStyleClasses.resultTableBodyCell}>
                          <Button
                            variant="contained"
                            disableElevation
                            onClick={() => {
                              setDocumentModalOpen({
                                documentId: document.id,
                                open: true
                              });
                            }}
                          >
                            보기
                          </Button>
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

      <DocumentApprovalModal
        modalOpen={documentModalOpen}
        setModalOpen={setDocumentModalOpen}
      />
    </>
  );
}

export default DocumentInboxPage;
