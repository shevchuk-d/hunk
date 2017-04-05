
DO $fill_lockers$
BEGIN
   FOR i IN 1..100 LOOP
      INSERT INTO lockers(number) VALUES (i);
   END LOOP;
END $fill_lockers$;

DO $fill_lockers_neighbors$
BEGIN
   FOR i in 1..100 LOOP
      IF EXISTS (SELECT locker_id FROM lockers WHERE locker_id = i + 1) THEN
         INSERT INTO lockers_neighbors(locker_id, neighbor_id) VALUES (i, i + 1);
         IF EXISTS (SELECT locker_id FROM lockers WHERE locker_id = i + 2) THEN
            INSERT INTO lockers_neighbors(locker_id, neighbor_id) VALUES (i, i + 2);
            IF ( i % 2 != 0 ) THEN
               IF EXISTS (SELECT locker_id FROM lockers WHERE locker_id = i + 3) THEN
                  INSERT INTO lockers_neighbors(locker_id, neighbor_id) VALUES (i, i + 3);
               END IF;
            END IF;
         END IF;
      END IF;
      IF EXISTS (SELECT locker_id FROM lockers WHERE locker_id = i - 1) THEN
         INSERT INTO lockers_neighbors(locker_id, neighbor_id) VALUES (i, i - 1);
         IF EXISTS (SELECT locker_id FROM lockers WHERE locker_id = i - 2) THEN
            INSERT INTO lockers_neighbors(locker_id, neighbor_id) VALUES (i, i - 2);
            IF ( i % 2 = 0 ) THEN
               IF EXISTS (SELECT locker_id FROM lockers WHERE locker_id = i - 3) THEN
                  INSERT INTO lockers_neighbors(locker_id, neighbor_id) VALUES (i, i - 3);
               END IF;
            END IF;
         END IF;
      END IF;
   END LOOP;
END $fill_lockers_neighbors$;

DO $fill_clients$
BEGIN
  FOR i in 1..100 LOOP
    INSERT INTO clients(name, sex) VALUES (concat('client', i), 'male');
  END LOOP;
END $fill_clients$;

do $smth$
BEGIN
  FOR j in 1..4 LOOP
--     DO $activate_visits$
--     BEGIN
      FOR i in 1..100 LOOP
        IF ( i % 5 =0 ) THEN
          INSERT INTO visits(start, finish, locker_id, client_id) VALUES (current_timestamp, NULL, i - j , i );
        END IF;
      END LOOP;
--     END $activate_visits$;

  END LOOP;
END $smth$;

    DO $finish_visits$
    BEGIN
FOR k in 1..400 LOOP
  IF ( k % 2 = 0 ) THEN
    UPDATE visits
    SET
      finish = current_timestamp + interval '14 minutes'
      , locker_id = NULL
    WHERE visit_id = k;
  END IF;
  IF ( k % 3 = 0 ) THEN
    UPDATE visits
    SET
      finish = current_timestamp + interval '14 minutes'
      , locker_id = NULL
    WHERE visit_id = k;
  END IF;
  IF ( k % 7 = 0 ) THEN
    UPDATE visits
    SET
      finish = current_timestamp + interval '14 minutes'
      , locker_id = NULL
    WHERE visit_id = k;
  END IF;
END LOOP;
    END $finish_visits$;


SELECT  *
FROM visits v
JOIN lockers l
  ON v.locker_id = l.locker_id
JOIN clients c
  ON v.client_id = c.client_id
WHERE finish ISNULL
AND v.start NOTNULL
AND (current_timestamp
  BETWEEN
    v.start
      AND
    v.start + INTERVAL '15 minutes'
or current_timestamp >=
    v.start + (SELECT avg(finish - start)
                 FROM visits
                 WHERE visits.client_id = c.client_id
    )::INTERVAL - INTERVAL '15 minutes')
;


INSERT INTO visits(start, finish, locker_id, client_id) VALUES (current_timestamp, NULL, 30, 30 );

SELECT  * -- avg(visits.finish - start)::INTERVAL
      FROM visits
      WHERE visits.client_id = 30
-- WHERE finish NOTNULL
;

SELECT  neighbor_id
FROM lockers_neighbors
WHERE locker_id in (
  SELECT  l.locker_id
  FROM visits v
    JOIN lockers l
      ON v.locker_id = l.locker_id
    JOIN clients c
      ON v.client_id = c.client_id
  WHERE finish ISNULL
        AND v.start NOTNULL
        AND (current_timestamp
             BETWEEN
             v.start
             AND
             v.start + INTERVAL '15 minutes'
             or current_timestamp >=
                v.start + (SELECT avg(finish - start)
                           FROM visits
                           WHERE visits.client_id = c.client_id
                )::INTERVAL - INTERVAL '15 minutes')
)
;

select
* from visits
where locker_id = 1
and finish ISNULL
;

select visit0_.visit_id as visit_id1_3_
  , visit0_.client_id as client_i4_3_
  , visit0_.finish as finish2_3_
  , visit0_.locker_id as locker_i5_3_
  , visit0_.start as start3_3_
from visits visit0_
where visit0_.locker_id=visit0_.visit_id
      and (visit0_.finish is null)