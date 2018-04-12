//
//  ReportMessagesVC.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 4/5/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit

class ReportMessagesVC: BaseVC,UITableViewDelegate,UITableViewDataSource {
   
    @IBOutlet weak var tableViewHeight : NSLayoutConstraint!
    @IBOutlet weak var tableView : UITableView!
    @IBOutlet weak var doneBtn : UIButton!
    
    @IBOutlet weak var vv : UIView!


    var messages = [ReportMessage]()
    var ad : AD!
    var adDetailsVC : AdDetailsVC!
    var selectedReportMessage : ReportMessage!

    override func viewDidLoad() {
        super.viewDidLoad()

        tableView.delegate = self
        tableView.dataSource = self
        tableViewHeight.constant = CGFloat(50 * messages.count)
        
        self.vv.addGestureRecognizer(UITapGestureRecognizer.init(target: self, action: #selector(self.cancel)))
    }
    
    @objc @IBAction func cancel(){
        self.dismiss(animated: true, completion: nil)
    }
    
    @IBAction func done(){
        self.showLoading()
        Communication.shared.report_item(ad_id: self.ad.ad_id.intValue, report_message_id: self.selectedReportMessage.report_message_id.intValue) { (res) in
            self.hideLoading()
            
            self.dismiss(animated: false, completion: nil)
            self.adDetailsVC.showErrorMessage(text: res.message)
            
//            self.dismiss(animated: false, completion: {
//                self.adDetailsVC.showErrorMessage(text: "SDSDSD")
//                self.adDetailsVC.showErrorMessage(text: "ReportSuccessful".localized)
//            })
        }
    }

    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return messages.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath)
        
        cell.textLabel?.text = self.messages[indexPath.row].msg
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 50
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        for i in tableView.visibleCells{
            i.accessoryType = .none
        }
        
        doneBtn.isHidden = false
        tableView.cellForRow(at: indexPath)?.accessoryType = .checkmark
        selectedReportMessage = self.messages[indexPath.row]
    }
    
    
    

}
