package com.alaphi.accountservice

trait Payload
trait AccountError {
  def description: String
}

case class AccountCreation(
  name: String,
  balance: Int = 0
) extends Payload

case class Account(
  number: String,
  name: String,
  balance: Int
) extends Payload

case class MoneyTransfer(
  destAccNum: String,
  transferAmount: Int
) extends Payload

case class TransferSuccess(
  sourceAccount: Account,
  destAccount: Account,
  transferAmount: Int
) extends Payload

case class TransferFailed(
  sourceAccNum: String,
  destAccNum: String,
  transferAmount: Int,
  description: String
) extends AccountError with Payload

case class AccountNotFound(
  accountNumber: String,
  description: String
) extends AccountError with Payload

case class DoMoneyTransfer(sourceAccNum: String, destAccNum: String, transferAmount: Int)
case class RemoveAccount(key: String)
case class GetAccount(key: String)

