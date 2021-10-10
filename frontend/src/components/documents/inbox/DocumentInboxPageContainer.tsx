import React, {useEffect, useState} from "react";
import DocumentInboxPage, {IInboxDocument} from "./DocumentInboxPage";
import {IDocumentModalOpen} from "../modal/DocumentModal";
import {request} from "../../../utils/requestUtils";
import {IDocumentApprovalModalOpen} from "../modal/DocumentApprovalModal";

const DocumentInboxPageContainer = () => {

  const [inboxDocuments, setInboxDocuments] = useState<IInboxDocument[]>([])
  const [documentModalOpen, setDocumentModalOpen] = useState<IDocumentApprovalModalOpen>({
    documentId: 0,
    open: false
  });

  const fetchInboxDocuments = async () => {
    const {data: inboxDocuments} = await request.get('/api/documents/outbox?drafterId=1')
    setInboxDocuments(inboxDocuments);
  }

  useEffect(() => {
    fetchInboxDocuments();
  }, [])

  const props = {
    inboxDocuments,
    documentModalOpen,
    setDocumentModalOpen
  }

  return (
    <>
      <DocumentInboxPage
        {...props}
      />
    </>
  );
}

export default DocumentInboxPageContainer;
