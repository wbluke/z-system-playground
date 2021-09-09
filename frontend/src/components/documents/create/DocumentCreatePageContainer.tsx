import React, {useEffect, useState} from "react";
import DocumentCreatePage, {IApprover, IDocumentCategorySelectItem, IDocumentPageParams} from "./DocumentCreatePage";
import {request} from "../../../utils/requestUtils";

const DocumentCreatePageContainer = () => {

  const [params, setParams] = useState<IDocumentPageParams>({
    category: '',
    title: '',
    contents: '',
  })
  const [categorySelectItems, setCategorySelectItems] = useState<IDocumentCategorySelectItem[]>([])
  const [approverSelectModalOpen, setApproverSelectModalOpen] = useState(false);
  const [approvers, setApprovers] = useState<IApprover[]>([]);

  const fetchCategorySelectItems = async () => {
    const {data: categoryItems} = await request.get('/api/documents/category')

    setCategorySelectItems(categoryItems)
  }

  const onConfirm = () => {
    console.log(params)
  }

  useEffect(() => {
    fetchCategorySelectItems();
  }, [])

  const props = {
    params,
    setParams,
    approvers,
    setApprovers,
    categorySelectItems,
    onConfirm,
    approverSelectModalOpen,
    setApproverSelectModalOpen,
  }

  return (
    <DocumentCreatePage
      {...props}
    />
  );
}

export default DocumentCreatePageContainer;
