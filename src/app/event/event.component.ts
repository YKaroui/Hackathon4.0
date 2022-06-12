import { Component, OnInit } from '@angular/core';
import { event } from '../shared/models/event';
import { EventService } from '../shared/services/event.service';
@Component({
  selector: 'app-event',
  templateUrl: './event.component.html',
  styleUrls: ['./event.component.css'],
})
export class EventComponent implements OnInit {
  events!: event[];
  constructor(private eventService: EventService) {}

  ngOnInit(): void {
    this.reloadData();
  }
  reloadData() {
    this.eventService.getAllEvent().subscribe((data) => {
      this.events = data;
      console.log(this.events[1].title);
    });
  }
}
