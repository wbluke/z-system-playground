import React, {useEffect, useState} from "react";
import DocumentPage, {IDocumentCategorySelectItem, IDocumentPageParams} from "./DocumentPage";

const DocumentPageContainer = () => {

  const [params, setParams] = useState<IDocumentPageParams>({
    category: '',
    title: '',
    contents: '',
  })
  const [categorySelectItems, setCategorySelectItems] = useState<IDocumentCategorySelectItem[]>([])

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
    onConfirm
  }

  return (
    <DocumentPage
      {...props}
    />
  );
}

export default DocumentPageContainer;
