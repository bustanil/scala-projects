package com.bustanil.todoscala

import cats.effect.kernel.Sync

import java.util.UUID

trait GenUUID[F[_]]:
  def uuid: F[UUID]

object GenUUID:
  def apply[F[_] : GenUUID]:  GenUUID[F] = summon
  
  given [F[_] : Sync]: GenUUID[F] = new GenUUID[F] {
    def uuid: F[UUID] = Sync[F].delay(UUID.randomUUID())
  }