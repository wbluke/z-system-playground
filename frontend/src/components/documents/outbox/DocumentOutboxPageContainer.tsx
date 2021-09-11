import React, {useEffect, useState} from "react";
import DocumentOutboxPage, {IOutboxDocument} from "./DocumentOutboxPage";
import {request} from "../../../utils/requestUtils";

const DocumentOutboxPageContainer = () => {

  const [outboxDocuments, setOutboxDocuments] = useState<IOutboxDocument[]>([])

  const fetchOutboxDocuments = async () => {
    const {data: outboxDocuments} = await request.get('/api/documents/outbox?drafterId=1')
    setOutboxDocuments(outboxDocuments);
  }

  useEffect(() => {
    fetchOutboxDocuments();
  }, [])

  const props = {
    outboxDocuments
  }

  return (
    <>
      <DocumentOutboxPage
        {...props}
      />
    </>
  );
}

export default DocumentOutboxPageContainer;
