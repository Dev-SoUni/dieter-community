import * as SC from './styled'

export const ErrorMessage = () => {
  return (
    <SC.Wrapper>
      <SC.ErrorIcon />
      <SC.TitleText>Oops!</SC.TitleText>
      <SC.MessageParagraph>
        서비스를 구성하는 중 문제가 발생했습니다.
      </SC.MessageParagraph>
    </SC.Wrapper>
  )
}
