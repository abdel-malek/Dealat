//
//  AdsVC.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/7/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit
import SideMenu
import CHIPageControl

class AdsVC: BaseVC {
    
    @IBOutlet weak var tableView : UITableView!
    @IBOutlet weak var collectionView : UICollectionView!
    @IBOutlet weak var pageControl : CHIPageControlAji!
    
    var searchBar:UISearchBar = UISearchBar(frame: CGRect.init(x : 0,y : 0,width : 200,height : 20))
    
    var x1 = -1
    var timer : Timer = Timer()
    
    var commericals : [String]{
        var ar = [String]()
        ar.append("ad_image_39")
        ar.append("ad_image_40")
        ar.append("ad_image_42")
        ar.append("ad_image_43")
        return ar
    }
    
    var ads : [AD]{
        var arr = [AD]()
        
        arr.append(AD(JSON: ["main_image" : "ad1"])!)
        arr.append(AD(JSON: ["main_image" : "ad2"])!)
        arr.append(AD(JSON: ["main_image" : "ad3"])!)
        arr.append(AD(JSON: ["main_image" : "ad4"])!)
        arr.append(AD(JSON: ["main_image" : "ad5"])!)
        arr.append(AD(JSON: ["main_image" : "ad6"])!)
        arr.append(AD(JSON: ["main_image" : "ad7"])!)
        arr.append(AD(JSON: ["main_image" : "ad8"])!)
        arr.append(AD(JSON: ["main_image" : "ad9"])!)
        
        return arr
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    override func setupViews() {
        self.tableView.delegate = self
        self.tableView.dataSource = self
        self.tableView.rowHeight = UITableViewAutomaticDimension
        self.tableView.estimatedRowHeight = 100
        self.tableView.addSubview(ref)
        
        collectionView.dataSource = self
        collectionView.delegate = self
        
        pageControl.numberOfPages = self.commericals.count
        self.timer.invalidate()
        self.timer = Timer.scheduledTimer(timeInterval: 3, target: self, selector: #selector(self.rotate), userInfo: nil, repeats: true)
        self.timer.fire()
        
        if #available(iOS 11.0, *) {
            //            searchBar.heightAnchor.constraint(equalToConstant: 44).isActive = true
        }
        
        self.searchBar.placeholder = "Search"
        self.searchBar.change(Theme.Font.Calibri)
        self.searchBar.sizeToFit()
        self.navigationItem.titleView = searchBar
        self.searchBar.delegate = self
        
    }
    
    
    @objc func rotate(){
        let cnt = self.commericals.count
        
        if(cnt > 1){
            if self.x1 != -1 {
                x1 = self.pageControl.currentPage + 1
            }else{
                self.x1 = 0
            }
        }
        else if (cnt == 1 || x1 > cnt - 1) {
            x1 = 0;
        }
        
        let u = x1 % cnt
        
        
        self.collectionView.setContentOffset(CGPoint(x: self.collectionView.frame.size.width * CGFloat(u), y: 0) , animated: true)
    }
    
    
}

extension AdsVC : UITableViewDelegate,UITableViewDataSource, UISearchBarDelegate{
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.ads.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let identifier = (indexPath.row % 2 == 0) ? "cell1" : "cell2"

        let cell = tableView.dequeueReusableCell(withIdentifier: identifier, for: indexPath) as! AdCell
        
        cell.tag = indexPath.row
        cell.ad = self.ads[indexPath.row]
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return UIScreen.main.bounds.width * 477 / 1128
    }
    
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let vc = self.storyboard?.instantiateViewController(withIdentifier: "AdDetailsVC")as! AdDetailsVC
        self.navigationController?.pushViewController(vc, animated: true)
        
    }
    
    func searchBarSearchButtonClicked(_ searchBar: UISearchBar) {
        searchBar.resignFirstResponder()
    }
    
}


extension AdsVC : UICollectionViewDataSource, UICollectionViewDelegate,UICollectionViewDelegateFlowLayout{
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return 4
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "cell", for: indexPath) as! CommericalCell
        cell.imageName = self.commericals[indexPath.row]
        return cell
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        return CGSize.init(width: collectionView.frame.width, height: collectionView.frame.height)
    }
    
    
    //Delegate With ScrollView (scroll item photos)
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if scrollView == self.collectionView{
            if self.commericals.count > 0 {
                if self.collectionView.visibleCells.count > 0{
                    let pp = round(self.collectionView.contentOffset.x / self.collectionView.frame.size.width)
                    self.pageControl.set(progress: Int(pp), animated: true)
                    
                    self.timer.invalidate()
                    self.timer = Timer.scheduledTimer(timeInterval: 3, target: self, selector: #selector(self.rotate), userInfo: nil, repeats: true)
                }
            }
        }
    }
    
    
    
}
