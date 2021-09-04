import React, {useEffect, useState} from "react";
import DocumentCreatePage, {IDocumentCategorySelectItem, IDocumentPageParams} from "./DocumentCreatePage";

const DocumentCreatePageContainer = () => {

  const [params, setParams] = useState<IDocumentPageParams>({
    category: '',
    title: '',
    contents: '',
  })
  const [categorySelectItems, setCategorySelectItems] = useState<IDocumentCategorySelectItem[]>([])
  const [approverSelectModalOpen, setApproverSelectModalOpen] = React.useState(false);

  const fetchCategorySelectItems = () => {
    const categoryItems = [
      {
        value: 'OPERATING_EXPENSES',
        text: '운영비'
      },
      {
        value: 'EDUCATION',
        text: '교육'
      }
    ]

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
